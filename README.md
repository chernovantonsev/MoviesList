# MoviesList
Необходимо разработать приложение, которое отображает список постеров к фильмам в виде
карточек. В вертикальной ориентации в строке по 3 карточки, в горизонтальной – 5. В приложении
также должен быть переключатель, который фильтрует фильмы по году: отобразить все фильмы
или отобразить только за 2020 год.

Приложение должно запрашивать данные по ссылке:
https://raw.githubusercontent.com/ar2code/apitest/master/movies.json
Стек технологий:
• Kotlin
• Kotlin coroutines
• Android jetpack (AndroidX, ViewModel, Lifecycles, LiveData, не используйте DataBinding).
• Glide
• Retrofit
• Clean Architecture
• Многомодульность
Требования к приложению:
Не используйте сторонние Frameworks, например, Moxy, - реализация своя. Приложение должно
кешировать данные от API в памяти после первого запуска. Фильтрация списка должна быть
«живой», достаточно стандартной анимации RecyclerView при добавлении/удалении. Хорошо,
если вы покажете понимание чистой архитектуры, построение логики приложения на основе
сценариев использования.