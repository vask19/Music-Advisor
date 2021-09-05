package advisor;

public class MusicAdvisor implements Advisor{

    @Override
    public String newAlbums() {
        return "---NEW RELEASES---\n" +
                "Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]";
    }

    @Override
    public String featured() {
        return "---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower";
    }

    @Override
    public String categories() {
        return "---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin";
    }

    @Override
    public String playlists(String nameOfCategory) {
        return "---MOOD PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll";
    }

    @Override
    public String exit() {
        return "---GOODBYE!---";
    }
}
