package recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Rating;

/**
 * Diese Klasse repr�sentiert einen Recommender, die die �hnlichkeit von
 * Benutzern berechnet und auf dieser Grundlage Vorschl�ge f�r Steine errechnet,
 * die einen Nutzer noch nicht bewertet hat.
 * 
 * @author benny
 * 
 */
public class Recommender {
	private static int MAX_STONE_ID;
	private static int MAX_USER_ID;

	// Matrix, welche die Ratings enth�lt (users_movies[userId][movieId] =
	// rating)
	private Integer usersStones[][] = null;
	// �hnlichkeitsmatrix
	private double usersUsers[][] = null;
	private List<Rating> ratings;

	public Recommender(List<Rating> ratings) {
		if (ratings == null || ratings.isEmpty()) {
			return;
		}
		this.ratings = ratings;
		Recommender.MAX_STONE_ID = getMaxStoneId() + 1;
		Recommender.MAX_USER_ID = getMaxUserId() + 1;
		initializeMatrixes();
		initializeRatingsMatrix();
		generateSimTable();

	}

	private Integer getMaxUserId() {
		Integer max = 0;
		for (Rating r : ratings) {
			if (r.getUserId() > max) {
				max = r.getUserId();
			}
		}
		return max;
	}
	
	private Integer getMaxStoneId() {
		Integer max = 0;
		for (Rating r : ratings) {
			if (r.getStoneId() > max) {
				max = r.getStoneId();
			}
		}
		return max;
	}

	/**
	 * Initialisiert die Matrizen auf denen die Berechnungen stattfinden.
	 */
	private void initializeMatrixes() {
		usersStones = new Integer[Recommender.MAX_USER_ID][Recommender.MAX_STONE_ID];
		usersUsers = new double[Recommender.MAX_USER_ID][Recommender.MAX_USER_ID];
	}

	/**
	 * Liest die Ratings der Nutzer eine Matrix ein.
	 * 
	 * @param ratings
	 *            Bewertungen des Nutzers
	 */
	private void initializeRatingsMatrix() {
		for (Rating r : ratings) {
			usersStones[r.getUserId()][r.getStoneId()] = r.getVoting();
		}
	}

	/**
	 * Berechnet das arithmetische Mittel aller abgegebenen Votings eines
	 * gegebenen Benutzers.
	 * 
	 * @param userId
	 * @return
	 */
	private double getMeanOfUser(int userId) {
		int numberOfVotes = 0;
		int absolut = 0;
		for (int i = 0; i < MAX_STONE_ID; i++) {
			if (usersStones[userId][i] != null && usersStones[userId][i] > 0) {
				absolut = absolut + usersStones[userId][i];
				numberOfVotes++;
			}
		}
		// Falls User nicht existiert und somit keine Bewertungen existieren
		if (numberOfVotes == 0) {
			return 0;
		} else {
			return ((double) absolut / (double) numberOfVotes);
		}
	}

	/**
	 * Erzeugt eine Matrix, die die �hnlickeiten der Nutzer untereinander
	 * enth�lt.
	 */
	private void generateSimTable() {
		for (int userA = 0; userA < MAX_USER_ID; userA++) {
			for (int userB = 0; userB < MAX_USER_ID; userB++) {
				// intersection enth�lt die Schnittmenge der Steine, f�r die
				// beide User ein Voting abgegeben haben.
				ArrayList<Integer> intersection = getIntersectionOf(userA,
						userB);
				double meanUserA = getMeanOfUser(userA);
				double meanUserB = getMeanOfUser(userB);
				double numerator = 0;
				double divisor1 = 0;
				double divisor2 = 0;
				if ((meanUserA != 0) && (meanUserB != 0)
						&& (intersection.size() > 0)) {
					int voteUserA = 0;
					int voteUserB = 0;

					for (int item = 0; item < intersection.size(); item++) {
						voteUserA = usersStones[userA][intersection.get(item)];
						voteUserB = usersStones[userB][intersection.get(item)];

						numerator = numerator
								+ ((voteUserA - meanUserA) * (voteUserB - meanUserB));
						divisor1 = divisor1
								+ Math.pow((voteUserA - meanUserA), 2);
						divisor2 = divisor2
								+ Math.pow((voteUserB - meanUserB), 2);
					}

					if ((numerator == 0) || (divisor1 == 0) || (divisor2 == 0)) {
						usersUsers[userA][userB] = 0;
					} else {
						usersUsers[userA][userB] = (numerator / ((Math
								.sqrt(divisor1) * Math.sqrt(divisor2))));
					}

				} else {
					usersUsers[userA][userB] = -99;
				}

			}

		}
	}

	/**
	 * Berechnet die Schnittmenge der der bewerteten Steine zweier User.
	 * 
	 * @param userA
	 * @param userB
	 * @return
	 */
	private ArrayList<Integer> getIntersectionOf(int userA, int userB) {
		ArrayList<Integer> intersection = new ArrayList<Integer>();
		for (int i = 0; i < MAX_STONE_ID; i++) {
			if ((usersStones[userA][i] != null)
					&& (usersStones[userB][i] != null)) {
				intersection.add(i);
			}
		}
		return intersection;
	}

	/**
	 * Generiert eine Liste von �hnlichen Nutzern und gibt diese - sortiert nach
	 * dem Grad der �hnlichkeit - zur�ck.
	 * 
	 * @param user
	 * @param neighbourhood
	 * @return
	 */
	private List<Neighbour> getOrderedNeighbours(int user, int neighbourhood) {
		ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();
		for (int i = 0; i < MAX_USER_ID; i++) {
			if (i != user) {
				if (usersUsers[user][i] != -99) {
					neighbours.add(new Neighbour(i, usersUsers[user][i],
							getIntersectionOf(user, i).size()));
				}
			}
		}
		Collections.sort(neighbours);
		// Falls weniger Nachbarn gefunden werden konnten, als gew�nscht
		if (neighbours.size() < neighbourhood) {
			neighbourhood = neighbours.size();
		}
		return neighbours.subList(0, neighbourhood);
	}

	/**
	 * Gibt eine Liste von Steinen zur�ck, die der Butzer mir userID = userId
	 * hat noch nicht gesehen hat. Diese Liste beruht auf der �hnlichkeit des
	 * Nutzers zu anderen.
	 * 
	 * @param userId
	 * @param neighbourSize
	 * @param recommendations
	 * @return
	 */
	private List<Integer> getRecommendations(int userId, int neighbourSize,
			int recommendations) {
		List<Neighbour> neighbours = getOrderedNeighbours(userId, neighbourSize);
		neighbourSize = neighbours.size();

		// Berechne zu jedem Stone die voraussichtliche Bewertung des Benutzers.
		// Nehme diese Liste, um eine
		// Empfehlung auszusprechen.

		// stoneCounter = 1; da kleineste stoneId = 1
		ArrayList<Prediction> stones = new ArrayList<Prediction>();
		for (int stoneCounter = 0; stoneCounter < MAX_STONE_ID; stoneCounter++) {
			if (usersStones[userId][stoneCounter] == null) {
				stones.add(new Prediction(stoneCounter, getPrediction(userId,
						stoneCounter, neighbours)));
			}
		}

		// sortiere nach G�te der Vorhersage
		Collections.sort(stones);

		List<Integer> retList = new ArrayList<Integer>();
		
		if(stones != null && !stones.isEmpty()){
			for (int i = 0; i < recommendations; i++) {
				retList.add(stones.get(i).stoneId);
			}			
		}else{ // wenn es keine Bewertungen anderer Benuter gibt
			Integer next = getMaxStoneId();
			for (int i = 0; i < recommendations; i++) {
				retList.add(++next);
			}
		}
		return retList;
	}

	/**
	 * 
	 * Gibt eine Vorhersage �ber das User-Rating eines bestimmten Steines.
	 * 
	 * @param userId
	 *            Id des User
	 * @param stoneId
	 *            Id des Steins
	 * @param neighbours
	 *            Nachbarn des momentan betrachteten Users
	 * @return
	 */
	private double getPrediction(int userId, int stoneId,
			List<Neighbour> neighbours) {
		double zaehler = 0;
		double nenner = 0;
		int neighbour_index;
		for (int i = 0; i < neighbours.size(); i++) {
			neighbour_index = neighbours.get(i).getIndex();
			if (usersStones[neighbour_index][stoneId] != null
					&& usersStones[neighbour_index][stoneId] > 0) {
				zaehler = zaehler
						+ (usersStones[neighbour_index][stoneId] * usersUsers[userId][neighbour_index]);
				nenner = nenner + (usersUsers[userId][neighbour_index]);
			}
		}

		// Stein wurde in der Nachbarschaft nicht bewertet
		if ((zaehler == 0) || (nenner == 0)) {
			return 0;
		} else {
			return Math.round(zaehler / nenner);
		}
	}

	private class Prediction implements Comparable<Prediction> {
		private Integer stoneId;
		private double predictionRate;

		public Prediction(Integer stoneId, double d) {
			this.stoneId = stoneId;
			this.predictionRate = d;
		}

		@Override
		public int compareTo(Prediction o) {
			return Double.compare(o.predictionRate, this.predictionRate);
		}
	}

	/**
	 * Gibt die ID des Steins zur�ck, die der Nutzer als n�chstes bewerten muss.
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getNextStone(Integer userId) {
		Integer initialized = getInitializationStone(userId);
		if (initialized != null) {
			return initialized;
		}

		int neighbourhood_size = 12;
		int recommendations = 1;

		List<Integer> recomms = getRecommendations(userId, neighbourhood_size,
				recommendations);
		Integer nextStoneId = recomms.get(0);
		return nextStoneId;
	}

	/**
	 * Pr�ft, ob der User mit userId bereits die Steine 1 bi 10 bewertet hat.
	 * Wenn nein, wird die ID des ersten Steins zur�ckgegeben, der nicht
	 * bewertet wurde. Andernfalls null.
	 * 
	 * @param userId
	 * @return
	 */
	private Integer getInitializationStone(Integer userId) {
		Set<Integer> rated = new HashSet<Integer>();
		if (this.ratings != null) {
			for (Rating r : ratings) {
				if (r.getUserId() == userId) {
					rated.add(r.getStoneId());
				}
			}			
		}

		if (rated.size() < 9) {
			return rated.size();
		}

		return null;
	}

}
