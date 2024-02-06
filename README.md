Monefy, мобільний додаток для управління фінансами на Android, створений для спрощення та покращення процесу відстеження особистих фінансів. Цей дружній до користувача додаток пропонує інтуїтивно зрозумілий інтерфейс, що робить його доступним навіть для новачків у бюджетуванні. Основні особливості включають:
  1.	Легке Відстеження Витрат: Введення витрат швидко та без зусиль. Додаток хатегоризує кожну витрату, дозволяючи користувачам зрозуміти, куди йде їхні гроші.
  2.	Створення та Моніторинг Бюджету: Користувачі можуть встановлювати місячні або власні періоди бюджетів, що дозволяє їм залишатися в межах фінансових обмежень та ефективніше заощаджувати.
  3.	Візуальні Інсайти: Monefy представляє фінансові дані через цікаві діаграми та графіки, пропонуючи чітке візуальне представлення доходів та витрат.
  4.	Управління Багатьма Рахунками: Додаток підтримує кілька рахунків, тому користувачі можуть окремо відстежувати особисті, сімейні та бізнес-фінанси.
  5.	Синхронізація: Для тих, хто використовує кілька пристроїв, Monefy надає можливості синхронізації, забезпечуючи актуальність фінансових даних на всіх пристроях.
  6.	Безпека: З вбудованими функціями безпеки, дані користувачів залишаються приватними та захищеними.
  7.	Налаштовувані Категорії: Користувачі можуть налаштовувати категорії витрат, роблячи додаток адаптивним до їх унікальних витратних звичок та потреб.
  8.	Підтримка Валют: Monefy підтримує різні валюти, що робить його підходящим для міжнародних користувачів або тих, хто подорожує за кордон.
  9.	Опції Резервного Копіювання та Експорту: Користувачі можуть робити резервні копії своїх даних та експортувати звіти для подальшого аналізу або обміну.
  10.	Функціональність Віджетів: Для швидкого доступу Monefy пропонує віджет, який можна додати на головний екран, дозволяючи швидше записувати витрати.
	
Загалом, Monefy - це комплексний та зручний інструмент для всіх, хто хоче взяти під контроль своє фінансове життя, пропонуючи збалансоване поєднання простоти та функціональності.

![1](https://github.com/neuronsoftml/Monefy/assets/77026653/773e7145-f9b7-4d38-9ad3-f29a1674bd45)
![2](https://github.com/neuronsoftml/Monefy/assets/77026653/a219cbbb-299c-477e-9fb3-3495231596ea)
![3](https://github.com/neuronsoftml/Monefy/assets/77026653/7093c48c-b5c5-4209-b7d6-a5ca8fc64c62)
![4](https://github.com/neuronsoftml/Monefy/assets/77026653/e73353c1-3496-4cbf-b6db-5d77463babb3)
![5](https://github.com/neuronsoftml/Monefy/assets/77026653/29a6d866-bed5-46b8-9a4e-8c0ffab80978)

Моя структура додатка досить добре організована, з досить глибокими рівнями абстракції і розділенням на пакети згідно функціональності. Ось деякі ключові аспекти, які можна прослідкувати:
1) Авторизація: Основні функції авторизації (реєстрація, вхід, відновлення пароля) організовані в пакеті authorization.
	
2) Функціональність: Цей пакет містить різні підпакети для різних функціональних областей, таких як операції з рахунками, доходами, робота з Firebase, мережеві запити тощо. Кожен підпакет містить контролери (controllers), адаптери (adapters), фрагменти (fragments) та інші класи, що відповідають за певну функціональність.
	
3) Інтерфейси: Є розділ інтерфейсів, який включає різні колбеки та інтерфейси для спрощення взаємодії між різними частинами додатка.
	
4) Моделі даних: Моделі даних організовані в підпакеті model, де кожен клас моделі представляє окремий тип даних у додатку, такі як рахунки, доходи, повідомлення, користувачі тощо.
	
5) Локальна база даних: Є окремий пакет для роботи з локальною базою даних, що дозволяє зберігати дані на пристрої користувача.
	
6) Головні активності: Є головні активності додатка, такі як MainActivity та LobbyActivity, які, очевидно, відповідають за головні екрани додатка.
	
7) UI: Є класи, які відповідають за оновлення інтерфейсу користувача, наприклад, для управління оновленням та помилками.

Ця структура дозволяє зберігати код додатка організованим і легко розширюваним. Всі функціональні частини розділені на окремі пакети, що дозволяє підтримувати чистоту коду і уникнути змішування різних обов'язків.
