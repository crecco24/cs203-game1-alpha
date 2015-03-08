cs203-game1
Game: Bubble Popper

Set up: A field containing various colored balls at the top, and a singular colored ball at the bottom along with a nextInQueue ball off to the side.

Purpose: Player inputs slope coordinates to "shoot off" the queued ball at the field of balls in play, aiming at balls of the same color. Once a ball is in contact with another ball it becomes part of a group. Groups of size 3 or greater dissappear.

Goal: Remove all balls from the field of play

Additional aspects: Field will be randomly generated with every game. Difficulty modes can increase the width of the field, and number of starting balls A counter will keep track of how many moves it takes for you to finish. Once balls come in contact with a wall it will stop

States of game: 3 states. 1) Playing- balls active in the field of play 2) Win- no balls active in the field of play 3) Lose- balls extend past a threshhold line on the field of play indicating game over

Pre game thoughts: 1) Balls can be designated int values for easy comparison 2) Balls must be assigned states of "inPlay" (once settled on the field), "outOfPlay" (either next to be shot or nextInQueu) ->therefore the lose state will only be initialized once a ball of "inPlay" state crosses that line 3) ...
