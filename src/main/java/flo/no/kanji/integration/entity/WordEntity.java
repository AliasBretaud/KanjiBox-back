package flo.no.kanji.integration.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Word entity persistent database object
 * 
 * @author Florian
 *
 */
@Entity
@Table(name = "word")
@Setter
@Getter
public class WordEntity {

	/** Database technical identifier **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Transcripted hiragana value of the word **/
	private String furiganaValue;

	/** Associated kanjis composing the word **/
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "word_kanji", joinColumns = { @JoinColumn(name = "word_id") }, inverseJoinColumns = {
			@JoinColumn(name = "kanji_id") })
	private List<KanjiEntity> kanjis;

	/**
	 * Word creation/update timestamp
	 */
	private LocalDateTime timeStamp;

	/** Word translation */
	private String translation;

	/**
	 * Word japanese value (literal kanjis and okuriganas)
	 */
	private String value;

	@PrePersist
	@PreUpdate
	private void setUp() {
		// Before each creation or upadte, setting current timestamp
		this.timeStamp = LocalDateTime.now();
	}
}
