# My Personal Project

## Sudoku game
This project is aimed at creating an interactive Sudoku game where users can generate new puzzles, solve them, and check their solutions. The application will offer different difficulty levels, allowing users to choose their preferred challenge. Once the puzzle is completed, the app will provide a feature to verify if the solution is correct, and it will also track the time taken to solve the puzzle, adding an additional layer of engagement.

This project is designed for individuals who enjoy brain games and puzzle-solving. It is especially appealing to those who are looking for a customizable Sudoku experience with features like difficulty settings and performance tracking. I am particularly interested in this project because it involves manipulating 2D arrays, which presents a unique challenge. Working with complex data structures in this way is both intriguing and enjoyable for me, providing an opportunity to enhance my problem-solving skills.

## User Story
- I want to be able to generate a Sudoku game that can be solved.
- I want to be able to decide the difficulty of the Sudoku game.
- I want to be able to check the answer after completing the game.
- I want to be able to save the progress of the game, history of games played.
- I want to be given the option to load my game list from file.
- I want to be able to get some hints when I get stuck.
- I want to be able to use note functions to note potential answer for each cell.
- I want to be able to measure time it took to solve the puzzle.


## Instructions for End User
- Start by creating new user/ load user from the database.
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by creating new game.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by loading game.
- You can locate my visual component in the main menu and the sudoku gameboard.
- You can save the state of my application by clicking save in the main option. It will save the whole data of the user.
- You can reload the state of my application by clicking load user in the main option. It will load all the infomation of saved user.

## Phase 4: Task 2
- The system logs whenever new user is created/loaded: "User: Ryan logged in"
- The system logs when a new game has been added to user's game list: "game has been added to the game list"
- The system logs when it loads list of game of a user: "Loaded Game list"
- The system logs when the sudoku gameboard is generated: "Generated new sudoku gameboard" 

## Phase 4: Task 3
- User class (association) has a field of Game class with multiplicity 0..*, and has a field of EventLog class with multiplicity 1
- Game class (association) has a field of Matrix class with multiplicity 1.
- Matrix (association) class has a field of Cell class with multiplicity 81 (9x9 array = 81 cells)
- User,Game,Matrix,Cell class all implements writable interface.
- EventLog implements Iterable and has a field of Event class with multiplicity 0..*.
- SudokuApp class has a field of JsonWriter and JsonReader both with multiplicity 1.
- SudokuCellDocumentFilter extends DocumentFilter.

If I had more time to work on the project, I would refactor the code so that it covers more edgecases.
At this point, the project works perfect but there are some actions that breaks the app (e.g. closing window with x button left top corner.)
If I had more time I would implement windowclosing handler to make it better.
Furthermore, I have long line of code in my ui package for SudokuApp Class. If I put in some more time, it can be refactored nicely.
