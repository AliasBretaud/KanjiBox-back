package flo.no.kanji.business.mapper;

import org.springframework.stereotype.Service;

import flo.no.kanji.business.model.Kanji;
import flo.no.kanji.integration.entity.KanjiEntity;

@Service
public class KanjiMapper {

	public Kanji toBusinessObject(KanjiEntity kanjiEntity) {
		Kanji kanji = new Kanji();
		kanji.setId(kanjiEntity.getId());
		kanji.setKunYomi(kanjiEntity.getKunYomi());
		kanji.setOnYomi(kanjiEntity.getOnYomi());
		kanji.setTranslations(kanjiEntity.getTranslations());
		kanji.setValue(kanjiEntity.getValue());

		return kanji;
	};

	public KanjiEntity toEntity(Kanji kanji) {
		KanjiEntity kanjiEntity = new KanjiEntity();
		kanjiEntity.setId(kanji.getId());
		kanjiEntity.setKunYomi(kanji.getKunYomi());
		kanjiEntity.setOnYomi(kanji.getOnYomi());
		kanjiEntity.setTranslations(kanji.getTranslations());
		kanjiEntity.setValue(kanji.getValue());

		return kanjiEntity;
	};
}