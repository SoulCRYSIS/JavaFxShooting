package logic;

import java.util.ArrayList;
import javafx.util.Pair;

public class WeightedRandom<E> {

	private ArrayList<Pair<E, Double>> data;
	private double total;

	public WeightedRandom() {
		this.data = new ArrayList<Pair<E, Double>>();
	}

	public void addElement(E e, double weight) {
		data.add(new Pair<E, Double>(e, weight));
		total += weight;
	}

	public E next() {
		double counter = 0;
		double rand = Math.random() * total;
		for (Pair<E, Double> p : data) {
			counter += p.getValue();
			if (counter >= rand) {
				return p.getKey();
			}
		}
		return null;
	}
}