CXX=g++
RM=rm -f

LIBS=-lSDL2

SRCS=main.cpp application.cpp stick_figure.cpp
OBJS=$(subst .cpp,.o,$(SRCS))

all: sdlapp

sdlapp: $(OBJS)
	$(CXX) -o sdlapp $(OBJS) $(LIBS)

sdlapp.o: main.cpp application.hpp

application.o: application.hpp application.cpp $(LIBS)

stick_figure.o: stick_figure.hpp stick_figure.cpp $(LIBS)

clean:
	$(RM) $(OBJS)

distclean: clean
	$(RM) sdlapp
