CXX=g++
RM=rm -f

LIBS=-lSDL2

SRCS=main.cpp application.cpp
OBJS=$(subst .cpp,.o,$(SRCS))

all: sdlapp

sdlapp: $(OBJS)
	$(CXX) -o sdlapp $(OBJS) $(LIBS)

sdlapp.o: main.cpp application.hpp

application.o: application.hpp application.cpp $(LIBS)

clean:
	$(RM) $(OBJS)

distclean: clean
	$(RM) sdlapp
