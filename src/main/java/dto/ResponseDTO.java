package dto;

public class ResponseDTO {

    private String message;

    public ResponseDTO(String salida) {
        this.message = salida;
    }

    public String getSalida() {
        return message;
    }

    public void setSalida(String salida) {
        this.message = salida;
    }


}
