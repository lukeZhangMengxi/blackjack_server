package mengxi.blackjack_server.http_msg;

public class ResultResponse {
    public int result;
    public long newBalance;

    public ResultResponse(int result, long balance) {
        this.result = result;
        this.newBalance = balance;
    }
}
