# UPPER-LOWER-GAME

A two-player card game implemented in Kotlin using the BoardGameWork (BGW) framework. This is a strategic card game where players compete by playing cards from their hands onto two central play stacks.

## 📋 Overview

Upper-Lower-Game is a card-based strategy game where two players take turns playing cards. The objective is to have the fewest cards remaining at the end of the game. Each player manages a hand of cards and a draw stack, while competing to play cards onto two central play stacks.

## 🎮 Game Rules

- **Players**: 2 players
- **Starting Setup**: 
  - Each player receives 5 cards in their hand
  - Each player has their own draw stack
  - Two play stacks are initialized in the center of the board
- **Gameplay**: Players take turns playing cards from their hand onto the available play stacks
- **Win Condition**: The player with fewer total cards (hand + draw stack) at the end wins

## 🏗️ Project Structure

```
src/
├── main/kotlin/
│   ├── Main.kt                           # Application entry point
│   ├── entity/                           # Game entities
│   │   ├── Card.kt                       # Card data class
│   │   ├── CardValue.kt                  # Card values (Ace-King)
│   │   ├── CardSuit.kt                   # Card suits (Hearts, Diamonds, Clubs, Spades)
│   │   ├── Game.kt                       # Game state
│   │   └── Player.kt                     # Player entity
│   ├── service/                          # Game logic layer
│   │   ├── RootService.kt                # Main service coordinator
│   │   ├── GameService.kt                # Game setup and management
│   │   ├── PlayerActionService.kt        # Player action handling
│   │   └── AbstractRefreshingService.kt  # UI refresh management
│   └── gui/                              # User interface
│       ├── SopraApplication.kt           # Main application window
│       ├── MainMenuScene.kt              # Main menu UI
│       ├── GameScene.kt                  # Game board UI
│       ├── ResultMenuScene.kt            # End game results UI
│       ├── CardImageLoader.kt            # Card graphics loader
│       ├── LabeledStackView.kt           # Custom card stack component
│       └── Refreshable.kt                # UI refresh interface
└── test/                                 # Unit tests
```

## 🛠️ Technologies & Dependencies

- **Language**: Kotlin 1.9.25
- **JVM**: Java 11
- **Build Tool**: Gradle (Kotlin DSL)
- **Game Framework**: [BoardGameWork (BGW)](https://github.com/tudo-aqua/bgw) v0.10
- **Testing**: JUnit 5
- **Documentation**: Dokka 1.9.20
- **Code Quality**: Detekt 1.23.7
- **Code Coverage**: Kover 0.6.1

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- Gradle (wrapper included)

### Running the Game

#### Using Gradle Wrapper (Recommended)

**On Unix/Linux/macOS:**
```bash
./gradlew run
```

**On Windows:**
```bash
gradlew.bat run
```

#### Building the Project

```bash
./gradlew build
```

#### Creating Distribution

```bash
./gradlew distZip
```
This creates a `distribution.zip` file in the `public/` directory.

## 🎯 How to Play

1. **Launch the game** using the run command
2. **Enter player names** in the main menu (or use default names)
3. **Click "START GAME"** to begin
4. **Take turns** playing cards:
   - Select a card from your hand
   - Click on one of the two central play stacks to place your card
5. **Draw cards** when your turn ends
6. **Game ends** when one player runs out of cards
7. **Winner** is determined by who has fewer total cards remaining

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

Test reports are generated in `public/test/`

### Code Coverage
```bash
./gradlew koverReport
```

Coverage reports are available in:
- HTML: `public/coverage/`
- XML: `public/coverage/report.xml`

### Code Quality (Detekt)
```bash
./gradlew detekt
```

Reports are generated in:
- Main code: `public/detekt/main.html`
- Test code: `public/detekt/test.html`

## 📚 Documentation

Generate API documentation using Dokka:

```bash
./gradlew dokkaHtml
```

Documentation will be available in `public/dokka/`

## 🏗️ Architecture

The project follows a clean architecture pattern with three main layers:

1. **Entity Layer** (`entity/`): Core game data models
2. **Service Layer** (`service/`): Business logic and game rules
3. **GUI Layer** (`gui/`): User interface and visualization

The **Observer Pattern** is implemented via the `Refreshable` interface to keep the UI synchronized with game state changes.

## 📝 Development

### Building from Source

1. Clone the repository
2. Navigate to the project directory
3. Build using Gradle:
   ```bash
   ./gradlew build
   ```

### Project Configuration

- **Main Class**: `MainKt`
- **Group ID**: `edu.udo.cs.sopra`
- **Version**: 1.0

## 🤝 Contributing

This appears to be an educational project (SoPra - Software Praktikum). Follow your course guidelines for contributions.

## 📄 License

Please check with the project owner for licensing information.

## 🐛 Known Issues

- Some JVM error log files (`hs_err_pid*.log`) are tracked in the repository and should be gitignored
- These can be safely deleted and added to `.gitignore`

## ✨ Credits

Built using the [BoardGameWork Framework](https://github.com/tudo-aqua/bgw) by TU Dortmund.

---

**Enjoy playing Upper-Lower-Game! 🎴**
