package cl.vmardones.chess.engine.game;

class GameState {

    private Turn currentTurn;

    private TurnMemento save(){
        return new TurnMemento(currentTurn);
    }

    private void load(final TurnMemento turnMemento){
        currentTurn = turnMemento.state();
    }
}
