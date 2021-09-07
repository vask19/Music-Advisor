package advisor;

public class Main {
    public static void main(String[] args) {
        Advisor advisor = new MusicAdvisor();
        AuthMusicAdvisorDecorator advisorDecorator = new AuthMusicAdvisorDecorator(advisor);
        System.out.println(advisorDecorator.newAlbums());
        System.out.println(advisorDecorator.featured());
        advisorDecorator.auth();
        System.out.println(advisorDecorator.newAlbums());
        System.out.println(advisorDecorator.exit());
        System.out.println(advisorDecorator.featured());
        String a = "df";
        a = new StringBuilder(a).reverse().toString();


    }

}
