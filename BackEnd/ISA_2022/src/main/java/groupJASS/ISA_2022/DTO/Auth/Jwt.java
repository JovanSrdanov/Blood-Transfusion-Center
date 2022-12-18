package groupJASS.ISA_2022.DTO.Auth;

// DTO koji enkapsulira generisani JWT i njegovo trajanje koji se vracaju klijentu
public class Jwt {

    private String jwt;


    public Jwt() {
        this.jwt = null;

    }

    public Jwt(String accessToken) {
        this.jwt = accessToken;

    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }


}