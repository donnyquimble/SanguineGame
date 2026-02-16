package crimson.model;

/**
 * Tracks what type of spot a CrimsonBoardSpot is.
 * EMPTY - An empty spot with neither card nor pawns.
 * PAWNS - A spot with a non-zero and non-negative
 *         number of pawns on it.
 * CARD - A spot with a card on it.
 */
public enum SpotType { EMPTY, PAWNS, CARD }