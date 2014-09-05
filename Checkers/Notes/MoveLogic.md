#Move logic
1. Assume an 8x8 structure.
2. Assume that black begins at row 0 and white begins at row 7
3. Game is over when there is all of one color has been eliminated from the board
   or the current player has no possible legal moves.
    * We need to check all game-over possibilities at the end of every turn.
4. **Uncrowned** black pieces may move to:
    * **Down-right**: (current-row + 1, current-column + 1)
    * **Down-left**: (current-row + 1, current-column - 1)
5. **Uncrowned** white pieces may move to:
    * **Up-right**: (current-row - 1, current-column + 1)
    * **Up-left**: (current-row - 1, current-column - 1)
6. **Crowned** pieces, of any color, may move to any of the above positions
7. A piece gets crowned by the following logic:
    * A black piece is crowned by reaching row 7
    * a white piece is crowned by reaching row 0
8. 
