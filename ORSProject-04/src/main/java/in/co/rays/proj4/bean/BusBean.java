package in.co.rays.proj4.bean;

public class BusBean extends BaseBean {

	private String passengerName;
	private String source;
	private String destination;
	private long ticketFare;
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public long getTicketFare() {
		return ticketFare;
	}
	public void setTicketFare(long ticketFare) {
		this.ticketFare = ticketFare;
	}
	
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return passengerName;
	}

}
