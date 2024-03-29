package flo.no.kanji.integration.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kanji entity persistent database object
 * @author Florian
 */
@Entity
@Table(name = "kanji")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KanjiEntity {

	/** Database technical identifier **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Kanji japanese style readings
	 */
	@ElementCollection
	@CollectionTable(name = "kanji_kun_yomi", joinColumns = @JoinColumn(name = "kanji_id"))
	@Column(name = "kun_yomi")
	private List<String> kunYomi;

	/**
	 * Kanji chinese style readings
	 */
	@ElementCollection
	@CollectionTable(name = "kanji_on_yomi", joinColumns = @JoinColumn(name = "kanji_id"))
	@Column(name = "on_yomi")
	private List<String> onYomi;

	/**
	 * Kanji translations
	 */
	@ElementCollection
	@CollectionTable(name = "kanji_translation", joinColumns = @JoinColumn(name = "kanji_id"))
	@Column(name = "translation")
	private List<String> translations;

	/**
	 * Kanji creation/update timestamp
	 */
	private LocalDateTime timeStamp;

	/**
	 * Kanji japanese value
	 */
	@Column(name = "`value`")
	private String value;

	/**
	 * Default method called before each persist or update operation
	 */
	@PrePersist
	@PreUpdate
	private void setUp() {
		// Before each creation or upadte, setting current timestamp
		this.timeStamp = LocalDateTime.now();
	}
}
