# COMP2042 Software Maintanence Coursework (Brick Breaker)

Name: Lim Zi Qin\
Student ID: 20204402\
Project Description: A space theme brick breaker game!


# Refactoring Activites

### 1. Implemented MVC Design Pattern in the game.
- Split classes into Model, View, Controller packages.
- Created debug package for DebugConsole & DebugPanel.
- Created mainGame package for main class.
- Makes it easier to maintain the code.

### 2. Re-named classes for better understanding.
- `Wall` -> `GameModel`
- `GameBoard` -> `GameView`
- `GameFrame` -> `GameController`

### 3. Split and extracted class to clear out responsibilities from `GameModel`
- The original `GameModel` class has too many responsibilities, therefore I extracted level-related code   into a new class
- Extracted level-related code into `LevelModel` class.

### 4. Beautify & improved presentation of code.
- Removed extra lines.
- Indenting the code for better readability.
- Comment on all method for easier understanding on first sight.

# Additions to the game!

### 1. Added Intro before going into the menu.
   ![Intro](/Images/IntroView.png)

### 2. Added Instruction & Scoreboard.
- Instruction will show players the game's control.
- Scoreboard will show the past score that playes achieved.

### 3. Added Background Music & Sound Effect to make the game interesting.
- Different Music for menu and in game.
- Sound effect for button & in game.

### 4. Added Animated Background to make the game to look more interesting & realistic.
- Different background for menu & in game.

### 5. Added Powerups (Fireball Powerup & Player Paddle Powerup) in game! 
- Makes the game more fun!
- Fireball Powerup will pierce-through any bricks for 3 seconds.
- Player Paddle Powerup will increase the player paddle width.

### 6. Added Punishment in game when player loses the ball.
- Decrease score if player loses the ball.
- Decrease player paddle width if player loses the ball.

### 7. Added Levels and Increased Ball Speed to make the game challenging.
- More bricks as level increase.
- Harder bricks as level increase.

# Documentation.

### 1. Included `JavaDocs` folder.
- Go to `JavaDocs` folder and click on any `.html` file.

### 2. Github Commit
- Github Project url : https://github.com/zqlim86/COMP2042_CW_hfyzl6

### 3. This README File!


# Github Commit History.
![GitCommit](/Images/GithubCommits-1.png)
![GitCommit](/Images/GithubCommits-2.png)
![GitCommit](/Images/GithubCommits-3.png)





