package Model;

/**
 * Created by Manpreet Gandhi on 5/22/2016.
 */
public class NaiveBayesModel {

    private double spamProbability;
    private double hamProbability;

    NaiveBayesModel()
    {

    }

    NaiveBayesModel(double spam, double ham)
    {
        spamProbability = spam;
        hamProbability = ham;
    }

    public double getHamProbability() {
        return hamProbability;
    }

    public void setHamProbability(double hamProbability) {
        this.hamProbability = hamProbability;
    }

    public double getSpamProbability() {
        return spamProbability;
    }

    public void setSpamProbability(double spamProbability) {
        this.spamProbability = spamProbability;
    }
}
