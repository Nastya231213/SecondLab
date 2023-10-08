package com.models;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customers", catalog = "secondlabproductstore", uniqueConstraints = @UniqueConstraint(columnNames = "Email"))
@NamedQueries({
	@NamedQuery(name = "Customers.findByEmail", query = "SELECT c FROM Customers c WHERE c.email = :emailParam")
	
})
public class Customers implements java.io.Serializable {

	private Integer customerId;
	private String username;
	private String email;
	private String answer;
	private String password;
    private String question;
	public Customers() {
	}

	public Customers(String username, String email, String answer, String password,String question) {
		super();
		this.username = username;
		this.email = email;
		this.answer = answer;
		this.password = password;
		this.question=question;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "CustomerID", unique = true, nullable = false)
	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "Username", unique = true, nullable = false)

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "Email", unique = true, nullable = false)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Password", unique = true, nullable = false)

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private Set<Orders> orderses = new HashSet<Orders>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customers")
	public Set<Orders> getOrderses() {
		return this.orderses;
	}

	@Column(name = "AnswerToQuestion")

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Column(name = "Question")

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setOrderses(Set<Orders> orderses) {
		this.orderses = orderses;
	}

}
