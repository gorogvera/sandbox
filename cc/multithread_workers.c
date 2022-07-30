#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <malloc.h>

pthread_mutex_t lock = PTHREAD_MUTEX_INITIALIZER;

const size_t num_threads = 4;
const size_t job_container_size = 10;

typedef struct job_t {
  size_t _a;
  void* _next;
} job;

typedef struct worker_thread_init_data_t {
  size_t my_tid;
} worker_thread_init_data;

// muxtex protected job containers
pthread_mutex_t locks[4];
job* jobs[4];


void* worker_func(void* tid)
{
  size_t my_tid = (size_t) tid;

  for (size_t i = 0; i < 10; ++i)
  {
    job* my_job = NULL;

    // get my next job from my container
    { // critical section, secured by lock
      pthread_mutex_lock(&locks[my_tid]);
      my_job = jobs[my_tid];
      if (my_job != NULL)
      {
        // detach head of the container
        // pop the first from the container
        jobs[my_tid] = my_job->_next;
        // don't point back to the head of the container anymore
        my_job->_next = NULL;
      }
      pthread_mutex_unlock(&locks[my_tid]);
    }

    // do my job and pass to next worker if any
    if (my_job != NULL)
    {
      { // do the job
        printf("[ %lu - %lu ] I have a job, |%.3lu| -> |%.3lu|!\n",
            my_tid, i, my_job->_a, my_job->_a + 1);
        my_job->_a++;
      }

      {
        // pass to next worker to continue the job
        size_t next_worker_id = (my_tid+1) % num_threads;
        printf("[ %lu - %lu ] passing job to thread: %lu\n",
          my_tid, i, next_worker_id);

        { // add to the end of the other worker's queue, the dirty way
          pthread_mutex_lock(&locks[next_worker_id]);

          job* jobs_head = jobs[next_worker_id];

          job** jobs_push_pos = &jobs[next_worker_id];
          while (*jobs_push_pos != NULL)
          {
            jobs_push_pos = (job**)&(*jobs_push_pos)->_next;
          }
          *jobs_push_pos = my_job;

          if (jobs_head != NULL)
          {
            jobs[next_worker_id] = jobs_head;
          }

          pthread_mutex_unlock(&locks[next_worker_id]);
        }
      }

    }
    else
    {
      printf("[ %lu - %lu ] I don't have any jobs!\n", my_tid, i);
    }
    fflush(stdout);

    // prevent busy waiting, although there is a better way to wait for jobs
    usleep(100000);
  }

  return NULL;
}

int main()
{
  pthread_t tid[num_threads];

  // create threads
  for (size_t i = 0; i < num_threads; ++i)
  {
    pthread_create(&tid[i], NULL, worker_func, (void*)i);
  }

  // create and add the initial jobs
  for (size_t i = 0; i < num_threads; ++i)
  {
    size_t tid = i;

    pthread_mutex_lock(&locks[tid]);
    jobs[tid] = malloc(sizeof(job));
    jobs[tid]->_a = 100 * i;
    jobs[tid]->_next = NULL;
    pthread_mutex_unlock(&locks[tid]);
  }

  // wait all threads
  for (size_t i = 0; i < num_threads; ++i)
  {
    pthread_join(tid[i], NULL);
  }

  return 0;
}
