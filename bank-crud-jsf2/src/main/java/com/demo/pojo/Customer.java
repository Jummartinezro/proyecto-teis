/**
 * @author <a href=mailto:volkodavav@gmail.com>volkodavav</a>
 */
package com.demo.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
		@javax.persistence.NamedQuery(name = "Customer.findAll", query = "select c from Customer c"),
		@javax.persistence.NamedQuery(name = "Customer.countAll", query = "select count(c) from Customer c"),
		@javax.persistence.NamedQuery(name = "Customer.deleteAll", query = "delete from Customer c") })
@Table(name = "CUSTOMER")
public class Customer implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Size(min = 3, max = 10)
	@Column(name = "NAME", nullable = false, length = 10)
	private String name;

	@NotNull
	@Past
	@Column(name = "BIRTHDAY", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date birthday;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@Size(max = 255)
	@Column(name = "ABOUT", length = 255)
	private String about;

	@Enumerated(EnumType.ORDINAL)
	private Card card;

	@NotNull
	@Min(0L)
	@Max(150L)
	@Column(name = "NUMBEROFCARDS", nullable = false)
	private Integer numberOfCards;

	@Column(name = "MAILINGLIST")
	private Boolean mailingList;

	@AssertTrue
	@Column(name = "LICENSE")
	private Boolean license;

	public Customer()
	{
		super();
	}

	public Customer(String name, Date birthday, Gender gender, String about,
			Card card, Integer numberOfCards, Boolean mailingList,
			Boolean license)
	{
		super();
		this.name = name;
		this.birthday = birthday;
		this.gender = gender;
		this.about = about;
		this.card = card;
		this.numberOfCards = numberOfCards;
		this.mailingList = mailingList;
		this.license = license;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof Customer))
		{
			return false;
		}
		Customer other = (Customer) obj;
		if (about == null)
		{
			if (other.about != null)
			{
				return false;
			}
		}
		else if (!about.equals(other.about))
		{
			return false;
		}
		if (birthday == null)
		{
			if (other.birthday != null)
			{
				return false;
			}
		}
		else if (!birthday.equals(other.birthday))
		{
			return false;
		}
		if (card != other.card)
		{
			return false;
		}
		if (gender != other.gender)
		{
			return false;
		}
		if (license == null)
		{
			if (other.license != null)
			{
				return false;
			}
		}
		else if (!license.equals(other.license))
		{
			return false;
		}
		if (mailingList == null)
		{
			if (other.mailingList != null)
			{
				return false;
			}
		}
		else if (!mailingList.equals(other.mailingList))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (numberOfCards == null)
		{
			if (other.numberOfCards != null)
			{
				return false;
			}
		}
		else if (!numberOfCards.equals(other.numberOfCards))
		{
			return false;
		}
		return true;
	}

	public String getAbout()
	{
		return this.about;
	}

	public Date getBirthday()
	{
		return this.birthday;
	}

	public Card getCard()
	{
		return this.card;
	}

	public Gender getGender()
	{
		return this.gender;
	}

	public Integer getId()
	{
		return this.id;
	}

	public Boolean getLicense()
	{
		return this.license;
	}

	public Boolean getMailingList()
	{
		return this.mailingList;
	}

	public String getName()
	{
		return this.name;
	}

	public Integer getNumberOfCards()
	{
		return numberOfCards;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((about == null) ? 0 : about.hashCode());
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((license == null) ? 0 : license.hashCode());
		result = prime * result
				+ ((mailingList == null) ? 0 : mailingList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((numberOfCards == null) ? 0 : numberOfCards.hashCode());
		return result;
	}

	public void setAbout(String about)
	{
		this.about = about;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public void setLicense(Boolean license)
	{
		this.license = license;
	}

	public void setMailingList(Boolean mailingList)
	{
		this.mailingList = mailingList;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNumberOfCards(Integer numberOfCards)
	{
		this.numberOfCards = numberOfCards;
	}

	@Override
	public String toString()
	{
		return String
				.format("Customer [id=%s, name=%s, numberOfCards=%s, birthday=%s, gender=%s, card=%s, about=%s, mailingList=%s, license=%s] [super.toString()=%s]",
						id, name, numberOfCards, birthday, gender, card, about,
						mailingList, license, super.toString());
	}
}