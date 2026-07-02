package in.co.rays.proj4.bean;

public class VehicleBean extends BaseBean {

	private long vehicleId;
	private String vehicleName;
	private String model;
	private String company;
	private long price;

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String getKey() {
		return String.valueOf(vehicleId);
	}

	@Override
	public String getValue() {
		return vehicleName;
	}
}