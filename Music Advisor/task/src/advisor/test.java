package advisor;

import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        String as = "error=AQCIb7VoWVBJKE76ieiOAdlCleWGzwiZCbcFKJWvwj4NzBv04Jhv9TpV_Yo1qe6984HcjZ62MSERW5ND0tDE-S5CT-ppGK7T7aNVGuBGydf_OsVJxNtkyK35xddZABOyC9FnTaqQ29QVVe9_iUVlGdsIj_9epAYHIg";
        Pattern p = Pattern.compile("code=\\.*");
        System.out.println(p.matcher(as).find());
    }
}
