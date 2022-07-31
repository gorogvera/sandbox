#pragma once

#include <SDL2/SDL.h>

class StickFigure
{
  public:
	  StickFigure();
	  ~StickFigure() = default;

	  void update(double);
	  void draw(SDL_Surface*);

  private:
    	double m_x;
    	double m_y;

    	SDL_Surface	*m_image;
    	SDL_Rect	m_position;
};
