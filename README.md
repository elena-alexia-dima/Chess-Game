A fully functional Chess game built in Java, featuring a complete graphical user 
interface and a clean, modular architecture based on classic object-oriented and 
design pattern principles.
The game supports all standard chess rules, tracks captured pieces in real time,
and provides visual move highlighting to assist the player.

Features:
-  Full chess rule implementation (all piece movements, check detection)
-  Interactive 8×8 graphical board built with Java Swing
-  Move highlighting — see all valid moves for a selected piece
-  Captured piece tracking displayed during gameplay
-  Real-time game state updates after every move
-  Clean architecture using 4 design patterns
-  
Design Patterns Used:
- Singleton: Ensures a single game instance manages the overall state
- Factory: Handles creation of different chess pieces dynamically
- Strategy: Encapsulates movement logic for each piece type
- Observer: Updates the UI in real time when the game state changes
- 
Technologies:
- Language: Java
- GUI Framework: Java Swing
