# CICCC-Group-Mini-Project

Group Mini Project of WMAD 202 - Intro to Object Oriented Programming (Java) in CICCC

- [Group Mini Project - The Chess (Google Docs)](https://docs.google.com/document/d/1ogqP0P6gmxsn0X68YACPYiX0JMZHc7QiaSYONysWZyw)

<hr>

# Group Mini Project (Instructions)

We've seen how Inheritance allows you to extend classes and add more functionality to them. Sometimes you not only want to extend the functionality of a class, but also modify it slightly in the child class.

For example, say you're building a Java chess game.

![chess_board](https://user-images.githubusercontent.com/7421329/67727772-ea768000-f9a7-11e9-9c4a-dc2b20f35aef.png)

A good Java design will have a **class** for each piece type:

![chess_pieces](https://user-images.githubusercontent.com/7421329/67727777-ee0a0700-f9a7-11e9-9906-8b2c18a642e3.png)

And they should all inherit from a common base class: `Piece`

#### Why?

Because according to the concept of polymorphism, you could represent the chess-board as a 2D array of `Piece` objects, and then each cell in the 2D array can contain any of the child classes that extend the `Piece` class.

###### Other classes

To store this 2D array we will need a class that represents the Game itself:

```java
class Game {
  private Piece[][] board;
  // Constructor creates an empty board
  public Game() {
    board = new Piece[8][8];
  }
}
```

And finally, a simple class called `Position` that has nothing more than a row value and a column value to represent a specific slot on the board.

That way, the `Piece` class can include a field variable of type `Position` that stores the current position of that piece on the board:

```java
class Piece {
  protected Position position;
}
```

Now, since all piece types inherit from the same parent class `Piece`, they will all share any methods declared in that class. (also protected fields are visible to subclasses)

For example:

It will be useful to have a method that checks if a potential movement of a piece is a valid one. Even though each piece type has a unique movement capability, any piece (regardless of its type) has to - at the very least - stay within the bounds of the chess board.

So, a good idea would be to include a method called `isValidMove` inside the `Piece` class that takes a potential new position and decides if that position is within the bounds of the chess board.

```java
class Piece {
  public boolean isValidMove(Position newPosition) {
    if(newPosition.getRow() > 0 && newPosition.getCol() > 0 
        && newPosition.getRow() < 8 && newPosition.getCol() < 8) {
      return true;
    } else {
      return false;
    }
  }
}
```

Since each of the child classes inherits from that `Piece` class, each will automatically include this method, which means you can call it from any of those classes directly.

For example:

```java
Queen queen = new Queen();
Position testPosition = new Position(3,10);
if (queen.isValidMove(testPosition)) {
   System.out.println("Yes, I can move there.");
} else {
   System.out.println("Nope, can't do!");
}
```

What we've done so far can be considered as a good start for checking the validity of the movement of a piece on the board. However, each piece type has a different pattern of allowed movements, which means that each of those child classes needs to implement the `isValidMove` method differently!

Luckily, Java not only offers a way to inherit a method from a parent class but also modify it to build your own custom version of it.

Let's see how.

### Method Override

When a class extends another class, all public methods declared in that parent class are automatically included in the child class without you doing anything.

However, you are allowed to **override** any of those methods. Overriding basically means re-declaring them in the child class and then re-defining what they should do.

Going back to our chess example, assume you're implementing the `isValidMove` method in the `Rook` class.

The `Rook` class extends the `Piece` class that already includes a definition of the `isValidMove` method.

```java
class Piece {
  public boolean isValidMove(Position newPosition) {
    if (newPosition.getRow() > 0 && newPosition.getCol() > 0 
        && newPosition.getRow() < 8 && newPosition.getCol() < 8) {
      return true;
    } else {
      return false;
    }
  }
}
```

Now let's implement a custom version of that method inside the `Rook` class:

```java
class Rook extends Piece {
   @Override
   public boolean isValidMove(Position newPosition) {
      if (newPosition.getCol() == this.position.getCol()
          || newPosition.getRow() == this.position.getCol()) {
         return true;
      } else {
         return false;
      }
   }
}
```

Notice how both method declarations are identical, except that the implementation in the child class has different code customizing the validity check for the Rook piece. Basically it's checking that the new position of the rook has the same column value as the current position (which means it's trying to move up or down), or has the same row position which means it has moved sideways, both valid movements for a Rook piece.  
Remember that `this.position.getCol()` and `this.position.getRow()` holding the current position of that piece.

We can also do the same for all the other piece types, like for example the Bishop class would have slightly different implementation:

```java
class Bishop extends Piece {
  public boolean isValidMove(Position newPosition) {
    if (Math.abs(newPosition.getCol() - this.position.getCol())
        == Math.abs(newPosition.getRow() - this.position.getRow())) {
      return true;
    } else {
      return false;
    }
  }
}
```

For the Bishop, since it can only move diagonally, we'd want to check that the number of vertical steps is equal to the number of horizontal steps. That is, the difference between the current and new column positions is the same as the difference between the current and new row positions.

I've used `Math.abs` which takes the absolute value of that difference to always be a positive value, allowing this check to work for all 4 diagonal directions.

Perfect, now try to override this method for the Queen class, remember, a Queen can move diagonally or in straight lines.

### super

SUPER! Not only because you managed to solve that exercise, but "super" is actually another Java keyword that we will be using next!

It is important to note that once you override a method, you basically ignore everything that was in the parent class and instead have your own custom implementation in the child class (literally overwriting it)!

In our case, we don't want to throw away the parent implementation. We actually want to continue to use the original method, and ADD the extra checks for each child class individually.

This is where we get to use the "super" keyword!

You are allowed to re-use the parent method in the child class by using the "super" keyword, followed by a dot and then the method name:

`super.isValidMove(position);`

Using the keyword super here means that we want to run the actual method in the super (or parent) class from inside the implementation in "this" class.

Which means in each of the child classes, before you get to check the custom movement, you can check if `super.isValidMove(position)` has returned false. If so, then there is no need to do any more checks and immediately return false; otherwise, continue checking.

The new implementation for the Rook class will look like this:

```java
class Rook extends Piece {
  boolean isValidMove(Position newPosition) {
    // First call the parent's method to check for the board bounds
    if (!super.isValidMove(position)) {
      return false;
    }
    // If we passed the first test then check for the specific rook movement
    if (newPosition.getCol() == this.position.getCol()
        && newPosition.getRow() == this.position.getRow()) {
      return true;
    } else {
      return false;
    }
  }
}
```

You can also use `super()` to call the parent's constructor. This is usually done when implementing the child's constructor. Typically you would want to first run everything in the parent's constructor then add more code in the child's constructor:

```java
class Rook extends Piece {
  // default constructor
  public Rook() {
    super(); // this will call the parent's constructor
  }
}
```

**Note:** If a child's constructor does not explicitly call the parent's constructor using super, the Java compiler automatically inserts a call to the default constructor of the parent class. If the parent class does not have a default constructor, you will get a compile-time error.

### Abstract Class

When you think about the `Piece` class, we use it for our subclasses to extend from it. We will mostly like never create an instance(object) of the `Piece` itself. In this case, we can make it abstract class. (It is a good practice to make your class `abstract` whenever you can! So we know it won’t be instantiated.)

`public abstract class Piece {...}`

<hr>

Here are some unicode characters you can use for the game  
(Just copy-paste each character)

♔♕♖♗♘♙  
♚♛♜♝♞♟

Now, we’ve got everything we need to know to complete this chess game.

Files to submit (list)

- Driver.java: to run your game
- Game.java: to include your game logic
- Piece.java
- King.java
- Queen.java
- Rook.java
- Bishop.java
- Knight.java
- Pawn.java
- Position.java

(This is just a guideline, you are allowed to modify the architecture of your chess game. Files you submit may differ from the above list.)
