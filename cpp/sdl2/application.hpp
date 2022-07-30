#pragma once

#include <SDL2/SDL.h>
#include <iostream>

class Application
{
  public:
    Application();
    ~Application();

    void loop();
    void update(double);
    void draw();

private:
    double m_image_x;
    double m_image_y;

    SDL_Surface	*m_image;
    SDL_Rect	m_image_position;

    SDL_Window	*m_window;
    SDL_Surface *m_window_surface;
    SDL_Event 	m_window_event;
};

SDL_Surface *load_surface(char const *path);
