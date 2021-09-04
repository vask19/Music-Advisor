package advisor.task1;

public class Core {


    public static void main(String[] args) {
        Core core = new Core();
        core.task1();
    }

    private void task1(){
        System.out.println(newAlbums());
        System.out.println(featured());
        System.out.println(categories());
        System.out.println(playlists("r"));
        System.out.println(exit());
    }
    private String featured(){
        return "---FEATURED---\n" +
                "Mellow Morning\n" +
                "Wake Up and Smell the Coffee\n" +
                "Monday Motivation\n" +
                "Songs to Sing in the Shower";
    }

    private String newAlbums(){
        return "---NEW RELEASES---\n" +
                "Mountains [Sia, Diplo, Labrinth]\n" +
                "Runaway [Lil Peep]\n" +
                "The Greatest Show [Panic! At The Disco]\n" +
                "All Out Life [Slipknot]";
    }

    private String categories(){

        return "---CATEGORIES---\n" +
                "Top Lists\n" +
                "Pop\n" +
                "Mood\n" +
                "Latin";
    }

    private String playlists(String nameOfCategory){
        return "---MOOD PLAYLISTS---\n" +
                "Walk Like A Badass  \n" +
                "Rage Beats  \n" +
                "Arab Mood Booster  \n" +
                "Sunday Stroll";
    }

    private String exit(){

        return "---GOODBYE!---";
    }

}


/*featured — список плейлистов Spotify с их ссылками, извлеченными из API;

new — список новых альбомов с исполнителями и ссылками на Spotify;

categories — список всех доступных категорий на Spotify (только их названия);

playlists C_NAME, где C_NAME-это имя категории. Список содержит плейлисты этой категории и их ссылки на Spotify;

exit завершает работу приложения.*/