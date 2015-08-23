package org.jeecg.learning.chapter4;


public class Address {

	private String id;

	private String street;

	private String city;

	private String country;

	public Address() {

	}

	/**
	 * Creates a new {@link Address} from the given street, city and country.
	 * 
	 * @param street must not be {@literal null} or empty.
	 * @param city must not be {@literal null} or empty.
	 * @param country must not be {@literal null} or empty.
	 */
	public Address(String street, String city, String country) {

		this.street = street;
		this.city = city;
		this.country = country;
	}

	/**
	 * Returns a copy of the current {@link Address} instance which is a new entity in terms of persistence.
	 * 
	 * @return
	 */
	public Address getCopy() {
		return new Address(this.street, this.city, this.country);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the street.
	 * 
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Returns the city.
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns the country.
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
