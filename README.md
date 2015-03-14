# AI_PROJ

## Project Members
- An luo
- Bingfeng liu

# Project Description

-Developing AI to play boardgame
##Rules of Boardgame

-Only two players , one is called 'WHITE' ,the Other one is 'Black'
-Board has a size of N*N which N is greater than 5 (i.e. N > 5)
-Top left corner is (0,0) , Bottom right coner is (N-1,N-1)
-Edges does not count as part of captured territory
  -Only free cells and opponent's cells count as captured cell
  -Pieces can't be placed in
-Board is read from stdin (i.e. java Main < input)

## Input Data Format
- First line is dimension of board
- 'W' == White
- 'B' == Black
- '-' == Captured Cell
- '+' == Free Cell

## Outcome Format

### First Line
- 'WHITE' ---> White is win
- 'BLACK' ---> Black is win
- 'Draw'  ---> White and Black captured same number of cells
- 'None'  ---> Game is not finished

### Second Line
-Number of cells captured by White

### Third Line
-Number of cells captured by Black

