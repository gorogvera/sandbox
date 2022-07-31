#pragma once

#include <SDL2/SDL.h>
#include <iostream>
#include "stick_figure.hpp"

class Application
{
  public:
    Application();
    ~Application();

    void loop();
    void update(double);
    void draw();

private:
    StickFigure m_stick_figure;

    SDL_Window	*m_window;
    SDL_Surface *m_window_surface;
    SDL_Event 	m_window_event;
};

SDL_Surface *load_surface(char const *path);
