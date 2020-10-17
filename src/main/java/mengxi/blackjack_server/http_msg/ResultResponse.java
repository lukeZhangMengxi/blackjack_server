package mengxi.blackjack_server.http_msg;

public class ResultResponse {
    public int result;
    public long newDeposit;

    public ResultResponse(int result, long deposit) {
        this.result = result;
        this.newDeposit = deposit;
    }
}
