CREATE TABLE country (
    id INT NOT NULL AUTO_INCREMENT COMMENT 'Идентифиактор гражданства',
	code VARCHAR(3) NOT NULL COMMENT 'Код страны' COLLATE 'utf8_bin',
	name VARCHAR(60) NOT NULL COMMENT 'Наименование гражданства' COLLATE 'utf8_bin',
    PRIMARY KEY (id));

CREATE TABLE doc (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор типа документа',
	code VARCHAR(3) NOT NULL COMMENT 'Номер документа',
	name VARCHAR(115) NOT NULL COMMENT 'Наименование документа' COLLATE 'utf8_bin',
    PRIMARY KEY (id));

CREATE TABLE organization (
	id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор организации',
	name VARCHAR(60) NOT NULL COMMENT 'Полное имя организации' COLLATE 'utf8_bin' ,
	inn INT(12) NOT NULL COMMENT 'ИНН организации',
	kpp INT(9) NOT NULL COMMENT 'КПП организации',
	short_name VARCHAR(30) COMMENT 'Сокращенное имя организации' COLLATE 'utf8_bin',
	PRIMARY KEY (id));

CREATE TABLE office (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор офиса',
	organization_id INT(11) NOT NULL COMMENT 'Идентификатор организации которой принадлежит офис',
	CONSTRAINT FK_link_organization FOREIGN KEY (organization_id) REFERENCES organization (id),
	INDEX IX_organizaton_organization_id (organization_id),
	name VARCHAR(50) NOT NULL COMMENT 'Наименование офиса' COLLATE utf8_bin,
	phone INT(11) NULL DEFAULT NULL COMMENT 'Телефон офиса',
	address VARCHAR(100) NOT NULL COMMENT 'Адрес офиса' COLLATE utf8_bin,
	is_active BOOLEAN DEFAULT NULL COMMENT 'Офис работает',
	is_main BOOLEAN DEFAULT NULL COMMENT 'Головной офис',
 	PRIMARY KEY (id));

 CREATE TABLE doc_data (
	id INT NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор документа',
	date DATE NOT NULL COMMENT 'Дата выдачи документа',
	number VARCHAR(10) NOT NULL COMMENT 'Номер документа',
	type INT(11) NOT NULL COMMENT 'Тип документа пользователя',
	CONSTRAINT FK_link_doc FOREIGN KEY (type) REFERENCES `doc` (id),
	PRIMARY KEY (id));

CREATE TABLE user (

	id INT NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор пользователя',
	first_name VARCHAR(15) NOT NULL COMMENT 'Имя' COLLATE 'utf8_bin',
	second_name VARCHAR(15) COMMENT 'Отчество' COLLATE 'utf8_bin',
	last_name VARCHAR(15) COMMENT 'Фамилия' COLLATE 'utf8_bin',
	position  VARCHAR(30) COMMENT 'Занимаемая должночть' COLLATE 'utf8_bin',
	phone INT(11) NULL DEFAULT NULL COMMENT 'Телефон полльзователя',
	identified BOOLEAN DEFAULT NULL COMMENT 'Признак идентифицированности пользователя',
	document INT NOT NULL COMMENT 'Документа пользователя',
	    CONSTRAINT FK_link_document FOREIGN KEY (document) REFERENCES doc_data (id),
	        INDEX IX_doc_doc_id (document),
	citizenship INT NOT NULL COMMENT 'Гражданство',
	    CONSTRAINT FK_link_citizenship FOREIGN KEY (citizenship) REFERENCES country (id),
	        INDEX IX_country_citizenship_id (citizenship),
	office_emp INT NOT NULL COMMENT 'Занимаемый офис',
	     CONSTRAINT FK_link_office FOREIGN KEY (office_emp) REFERENCES office (id),
	        INDEX IX_office_office_id (office_emp),
	PRIMARY KEY (id));



ALTER TABLE country COMMENT='Справочник кодов стран гражданства';
ALTER TABLE doc COMMENT='Справочник типов докуента';
ALTER TABLE organization COMMENT='Список организаций';
ALTER TABLE office COMMENT='Список офисов организации';
ALTER TABLE user COMMENT='Список работников офиса';
ALTER TABLE doc_data COMMENT='Данные документа пользователя';