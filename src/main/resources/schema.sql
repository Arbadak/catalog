CREATE TABLE country (
    id INT PRIMARY KEY AUTO_INCREMENT,
	code VARCHAR(3) NOT NULL,
	name VARCHAR(60) NOT NULL);
    COMMENT ON COLUMN country.id IS 'Идентифиактор гражданства';
    COMMENT ON COLUMN country.code IS 'Код страны';
    COMMENT ON COLUMN country.name IS 'Наименование гражданства';

CREATE TABLE doc (
	id INT PRIMARY KEY AUTO_INCREMENT,
	code VARCHAR(3) NOT NULL,
	name VARCHAR(115) NOT NULL);
    COMMENT ON COLUMN doc.id IS 'Идентификатор типа документа';
    COMMENT ON COLUMN doc.code IS 'Номер документа';
    COMMENT ON COLUMN doc.name IS 'Наименование документа';


CREATE TABLE organization (
	id INT PRIMARY KEY AUTO_INCREMENT,
	OPTLOCK INT default 0,
	name VARCHAR(60) NOT NULL,
	inn VARCHAR(12) NOT NULL,
	kpp VARCHAR(9) NOT NULL,
	short_name VARCHAR(30));
	COMMENT ON COLUMN organization.OPTLOCK IS 'Поле версии';
    COMMENT ON COLUMN organization.id IS 'Идентификатор организации';
    COMMENT ON COLUMN organization.name IS 'Полное имя организации';
    COMMENT ON COLUMN organization.inn IS 'ИНН организации';
    COMMENT ON COLUMN organization.kpp IS 'КПП организации';
    COMMENT ON COLUMN organization.short_name IS 'Сокращенное имя организации';


CREATE TABLE office (
	id INT PRIMARY KEY AUTO_INCREMENT,
    OPTLOCK INT default 0,
	organization_id INT NOT NULL,
	CONSTRAINT FK_link_organization FOREIGN KEY (organization_id) REFERENCES organization (id),
	name VARCHAR(50) NOT NULL,
	phone VARCHAR(11) NULL DEFAULT NULL,
	address VARCHAR(100) NOT NULL,
	is_active BOOLEAN DEFAULT NULL,
	is_main BOOLEAN DEFAULT NULL);
 	COMMENT ON COLUMN office.OPTLOCK IS 'Поле версии';
    CREATE INDEX IX_organization_organization_id ON office (organization_id);
    COMMENT ON COLUMN office.id IS 'Идентификатор офиса';
    COMMENT ON COLUMN office.organization_id IS 'Идентификатор организации которой принадлежит офис';
    COMMENT ON COLUMN office.name IS 'Наименование офиса';
    COMMENT ON COLUMN office.phone IS 'Телефон офиса';
    COMMENT ON COLUMN office.address IS 'Адрес офиса';
    COMMENT ON COLUMN office.is_active IS 'Офис работает';
    COMMENT ON COLUMN office.is_main IS 'Головной офис';

 CREATE TABLE doc_data (
	id INT PRIMARY KEY AUTO_INCREMENT,
	OPTLOCK INT default 0,
	date DATE NOT NULL,
	number VARCHAR(10) NOT NULL,
	doc_Type INT NOT NULL,
	CONSTRAINT FK_link_doc FOREIGN KEY (doc_Type) REFERENCES `doc` (id));
	COMMENT ON COLUMN doc_data.OPTLOCK IS 'Поле версии';
	COMMENT ON COLUMN doc_data.id IS 'Идентификатор документа';
	COMMENT ON COLUMN doc_data.date IS  'Дата выдачи документа';
	COMMENT ON COLUMN doc_data.number IS  'Номер документа';
	COMMENT ON COLUMN doc_data.doc_Type IS  'Тип документа пользователя';


CREATE TABLE user (
	id INT PRIMARY KEY AUTO_INCREMENT,
	OPTLOCK INT default 0,
	first_name VARCHAR(15) NOT NULL,
	second_name VARCHAR(15),
	last_name VARCHAR(15),
	position  VARCHAR(30),
	phone VARCHAR(11) NULL DEFAULT NULL,
	is_identified BOOLEAN DEFAULT NULL,
	document INT NOT NULL,
	CONSTRAINT FK_link_document FOREIGN KEY (document) REFERENCES doc_data (id),
	citizenship INT NOT NULL,
	CONSTRAINT FK_link_citizenship FOREIGN KEY (citizenship) REFERENCES country (id),
	office_emp INT,
    CONSTRAINT FK_link_office FOREIGN KEY (office_emp) REFERENCES office (id));
	COMMENT ON COLUMN user.OPTLOCK IS 'Поле версии';
	CREATE INDEX IX_doc_doc_id ON user (document);
    CREATE INDEX IX_country_citizenship_id ON user (citizenship);
    CREATE INDEX IX_office_office_id ON user (office_emp);

    COMMENT ON COLUMN user.id IS 'Идентификатор пользователя';
    COMMENT ON COLUMN user.first_name IS 'Имя';
    COMMENT ON COLUMN user.second_name IS 'Отчество';
    COMMENT ON COLUMN user.last_name IS 'Фамилия';
    COMMENT ON COLUMN user.position IS 'Занимаемая должноcть';
    COMMENT ON COLUMN user.phone IS  'Телефон полльзователя';
    COMMENT ON COLUMN user.is_identified IS  'Признак идентифицированности пользователя';
    COMMENT ON COLUMN user.document IS  'Документ пользователя';
    COMMENT ON COLUMN user.citizenship IS  'Гражданство';
    COMMENT ON COLUMN user.office_emp IS  'Занимаемый офис';


COMMENT ON TABLE country IS 'Справочник кодов стран гражданства';
COMMENT ON TABLE doc IS 'Справочник типов докуента';
COMMENT ON TABLE organization IS 'Список организаций';
COMMENT ON TABLE office IS 'Список офисов организации';
COMMENT ON TABLE user IS 'Список работников офиса';
COMMENT ON TABLE doc_data IS 'Данные документа пользователя';

REPLACE INTO `organization` (`name`, `inn`, `kpp`, `short_name`) VALUES
	( 'ООО Вектор', '123456789012', 321321321, 'Вектор'),
	( 'ЗАО РОГА и КОПЫТА', '234567890123', 333333333, 'РОГАиКО'),
	( 'АО ГроссГаз', '345678901234', 555555555, 'ГАз'),
	( 'ЗАО Полноименная организация', '456789012345', 123123123, 'ПолнноеО'),
	( 'ПАО Луч добра', '567890123456', 818081818, 'Луч'),
	( 'ЗАО Дальние Берега', '789012345678', 808081808, 'Берега'),
	( 'ПАО Зеленый луч', '890123456789', 333444333, 'Луч'),
	( 'ООО Зеркало перспективы', '901234567890', 808080808, 'Зеркало');

REPLACE INTO `office` ( `address`, `is_active`, `is_main`, `name`, `phone`, `organization_id`) VALUES
	( 'Пырловка 10', 1, 1, 'ГО РОГА и КО', 5551111, 2),
	( 'Пырловка 66', 1, 0, 'Филиалс Северный РОГ и ко', 555222, 2),
	( 'Ново-Свистуново', 1, 1, 'АО Центр объегоривания клиентов', 557222, 1),
	( 'Волчехвостск 105', 1, 1, 'Газмяс', 554111, 3),
	( 'Хохлотушкина 55', 1, 1, 'Колотушкино-великий', 245847, 4),
	( 'Мухлеевка 556', 0, 1, 'ПАО Зеленый луч', 12800, 7),
	( 'Кукуево 726', 1, 1, 'Зеркало перспективы', 142454, 8),
	( 'Кукуево 55', 1, 1, 'Дальние Берега', 3454634, 6),
	( 'Жилино 36', 1, 1, 'Луч добра', 3564654, 8),
	( 'Улетаевка 1', 1, 0, 'Особо зеленый оффис', 3564653, 7),
	( 'Мухловка 22', 1, 0, 'Не особо зеленый оффис', 3564677, 7);

replace into `doc` (`code`, `name`) VALUES
('03','Свидетельство о рождении'),
('07','Военный билет'),
('08','Временное удостоверение, выданное взамен военного билета'),
('10','Паспорт иностранного гражданина'),
('11','Свидетельство о рассмотрении ходатайства о признании лица
беженцем на территории Российской Федерации по существу'),
('12','Вид на жительство в Российской Федерации'),
('13','Удостоверение беженца'),
('15','Разрешение на временное проживание в Российской Федерации'),
('18','Свидетельство о предоставлении временного убежища на территории
Российской Федерации'),
('21','Паспорт гражданина Российской Федерации'),
('23','Свидетельство о рождении, выданное уполномоченным органом
иностранного государства'),
('24','Удостоверение личности военнослужащего Российской Федерации');

replace into `country` (`code`, `name`) VALUES
('004','Афганистан'),
('008','Албания'),
('010','Антарктика'),
('012','Алжир'),
('016','Американское Самоа'),
('020','Андорра'),
('024','Ангола'),
('028','Антигуа и Барбуда'),
('031','Азербайджан'),
('032','Аргентина'),
('036','Австралия'),
('040','Австрия'),
('044','Багамские Острова'),
('048','Бахрейн'),
('050','Бангладеш'),
('051','Армения'),
('052','Барбадос'),
('056','Бельгия'),
('060','Бермудские Острова'),
('064','Бутан'),
('068','Боливия'),
('070','Босния и Герцеговина'),
('072','Ботсвана'),
('074','Остров Буве'),
('076','Бразилия'),
('084','Белиз'),
('086','Британская территория в Индийском океане'),
('090','Соломоновы Острова'),
('092','Британские Виргинские острова'),
('096','Бруней'),
('100','Болгария'),
('104','Мьянма'),
('108','Бурунди'),
('112','Белоруссия'),
('116','Камбоджа'),
('120','Камерун'),
('124','Канада'),
('132','Кабо-Верде'),
('136','Каймановы острова'),
('140','Центральноафриканская Республика'),
('144','Шри-Ланка'),
('148','Чад'),
('152','Чили'),
('156','Китайская Народная Республика'),
('162','Остров Рождества'),
('166','Кокосовые острова'),
('170','Колумбия'),
('174','Коморы'),
('175','Майотта'),
('178','Республика Конго'),
('180','Демократическая Республика Конго'),
('184','Острова Кука'),
('188','Коста-Рика'),
('191','Хорватия'),
('192','Куба'),
('196','Кипр'),
('203','Чехия'),
('204','Бенин'),
('208','Дания'),
('212','Доминика'),
('214','Доминиканская Республика'),
('218','Эквадор'),
('222','Сальвадор'),
('226','Экваториальная Гвинея'),
('231','Эфиопия'),
('232','Эритрея'),
('233','Эстония'),
('234','Фарерские острова'),
('238','Фолклендские острова'),
('239','Южная Георгия и Южные Сандвичевы острова'),
('242','Фиджи'),
('246','Финляндия'),
('248','Аландские острова'),
('250','Франция'),
('254','Французская Гвиана'),
('258','Французская Полинезия'),
('260','Французские Южные и Антарктические территории'),
('262','Джибути'),
('266','Габон'),
('268','Грузия'),
('270','Гамбия'),
('275','Палестина'),
('276','Германия'),
('288','Гана'),
('292','Гибралтар'),
('296','Кирибати'),
('300','Греция'),
('304','Гренландия'),
('308','Гренада'),
('312','Гваделупа'),
('316','Гуам'),
('320','Гватемала'),
('324','Гвинея'),
('328','Гайана'),
('332','Республика Гаити'),
('334','Остров Херд и острова Макдональд'),
('336','Ватикан'),
('340','Гондурас'),
('344','Гонконг'),
('348','Венгрия'),
('352','Исландия'),
('356','Индия'),
('360','Индонезия'),
('364','Иран'),
('368','Ирак'),
('372','Ирландия'),
('376','Израиль'),
('380','Италия'),
('384','Кот-д’Ивуар'),
('388','Ямайка'),
('392','Япония'),
('398','Казахстан'),
('400','Иордания'),
('404','Кения'),
('408','КНДР'),
('410','Республика Корея'),
('414','Кувейт'),
('417','Киргизия'),
('418','Лаос'),
('422','Ливан'),
('426','Лесото'),
('428','Латвия'),
('430','Либерия'),
('434','Ливия'),
('438','Лихтенштейн'),
('440','Литва'),
('442','Люксембург'),
('446','Макао'),
('450','Мадагаскар'),
('454','Малави'),
('458','Малайзия'),
('462','Мальдивы'),
('466','Мали'),
('470','Мальта'),
('474','Мартиника'),
('478','Мавритания'),
('480','Маврикий'),
('484','Мексика'),
('492','Монако'),
('496','Монголия'),
('498','Молдавия'),
('499','Черногория'),
('500','Монтсеррат'),
('504','Марокко'),
('508','Мозамбик'),
('512','Оман'),
('516','Намибия'),
('520','Науру'),
('524','Непал'),
('528','Нидерланды'),
('531','Кюрасао'),
('533','Аруба'),
('534','Синт-Мартен'),
('535','Бонэйр, Синт-Эстатиус и Саба'),
('540','Новая Каледония'),
('548','Вануату'),
('554','Новая Зеландия'),
('558','Никарагуа'),
('562','Нигер'),
('566','Нигерия'),
('570','Ниуэ'),
('574','Норфолк'),
('578','Норвегия'),
('580','Северные Марианские острова'),
('581','Внешние малые острова США'),
('583','Микронезия'),
('584','Маршалловы Острова'),
('585','Палау'),
('586','Пакистан'),
('591','Панама'),
('598','Папуа — Новая Гвинея'),
('600','Парагвай'),
('604','Перу'),
('608','Филиппины'),
('612','Острова Питкэрн'),
('616','Польша'),
('620','Португалия'),
('624','Гвинея-Бисау'),
('626','Восточный Тимор'),
('630','Пуэрто-Рико'),
('634','Катар'),
('638','Реюньон'),
('642','Румыния'),
('643','Россия'),
('646','Руанда'),
('652','Сен-Бартелеми'),
('654','Острова Святой Елены, Вознесения и Тристан-да-Кунья'),
('659','Сент-Китс и Невис'),
('660','Ангилья'),
('662','Сент-Люсия'),
('663','Сен-Мартен (Франция)'),
('666','Сен-Пьер и Микелон'),
('670','Сент-Винсент и Гренадины'),
('674','Сан-Марино'),
('678','Сан-Томе и Принсипи'),
('682','Саудовская Аравия'),
('686','Сенегал'),
('688','Сербия'),
('690','Сейшельские Острова'),
('694','Сьерра-Леоне'),
('702','Сингапур'),
('703','Словакия'),
('705','Словения'),
('706','Сомали'),
('710','Южно-Африканская Республика'),
('716','Зимбабве'),
('724','Испания'),
('728','Южный Судан'),
('729','Судан'),
('732','Западная Сахара'),
('740','Суринам'),
('744','Шпицберген и Ян-Майен'),
('748','Свазиленд'),
('752','Швеция'),
('756','Швейцария'),
('760','Сирия'),
('762','Таджикистан'),
('764','Таиланд'),
('768','Того'),
('772','Токелау'),
('776','Тонга'),
('780','Тринидад и Тобаго'),
('784','Объединённые Арабские Эмираты'),
('788','Тунис'),
('792','Турция'),
('795','Туркмения'),
('796','Теркс и Кайкос'),
('798','Тувалу'),
('800','Уганда'),
('804','Украина'),
('807','Республика Македония'),
('818','Египет'),
('826','Великобритания'),
('831','Гернси'),
('832','Джерси'),
('833','Остров Мэн'),
('834','Танзания'),
('840','Соединённые Штаты Америки'),
('850','Виргинские Острова'),
('854','Буркина-Фасо'),
('858','Уругвай'),
('860','Узбекистан'),
('862','Венесуэла'),
('876','Уоллис и Футуна'),
('882','Самоа'),
('887','Йемен'),
('894','Замбия');

Replace into `DOC_DATA` (`date`, `number`, `doc_Type`) VALUES
('1976-12-03','2801763008','10'),
('1961-05-05','2346923455','7'),
('1991-08-15','5685942428','1'),
('1937-02-28','9872346238','2');

Replace into `USER` (`FIRST_NAME`, `SECOND_NAME`, `LAST_NAME`, `POSITION`, `PHONE`, `IS_IDENTIFIED`, `DOCUMENT`, `CITIZENSHIP`, `OFFICE_EMP`) VALUES

('Поздняков','Андрей','Петрович','Директор','79010000001','1','1','230','6'),
('Василий','Алибаба','Алибабаевич','Дворник','79010000002','1','2','242','10'),
('Кутузов','Герман','Данилович','Бухгалтер','79010000003','1','3','187','11'),
('Нэп','Вилор','Мавзолеевич','Сторож','79010000004','1','4','187','11');