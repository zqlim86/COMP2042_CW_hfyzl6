# COMP2042 Software Maintanence Coursework (Brick Breaker)

Name: Lim Zi Qin\
Student ID: 20204402\
Project Description: A space theme brick breaker game!


# Refactoring Activites

### 1. Implemented MVC Design Pattern in the game.
   * Split classes into Model, View, Controller packages.
   * Created debug package for DebugConsole & DebugPanel.
   * Created mainGame package for main class.
   * Makes it easier to maintain the code.

### 2. Re-named classes for better understanding.
   * `Wall` -> `GameModel`
   * `GameBoard` -> `GameView`
   * `GameFrame` -> `GameController`

### 3. Split and extracted class to clear out responsibilities from `GameModel`
   * The original `GameModel` class has too many responsibilities, therefore I extracted level-related code   into a new class
   * Extracted level-related code into `LevelModel` class.

### 4. Beautify & improved presentation of code.
   * Removed extra lines.
   * Indenting the code for better readability.
   * Comment on all method for easier understanding on first sight.

# Additions to the game!

### 1. Added Intro before going into the menu.
   ![Intro](/Images/IntroView.png)

### 2. Added Instruction & Scoreboard button in home menu.
	![HomeMenu](/Images/MenuView.png)