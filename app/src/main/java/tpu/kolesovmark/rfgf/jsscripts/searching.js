
      setTimeout(function() {
        (function() {
          document.querySelector('input[name="login"]').value = 'kolesovmark_tpu';
          document.querySelector('input[type="password"]').value = 'c@jh82Rh>Q>?Pi)';
          document.querySelector('input[type="submit"]').click();
        })();

        setTimeout(function() {
          (function() {
            document.querySelector('input[name="ftext"]').value = 'Москва';
            document.querySelector('input[type="submit"]').click();
          })();

          setTimeout(function() {
            // Здесь можно выполнить какие-то действия с HTML страницы
            // Например, отправить на сервер, сохранить в файл и т.д.
            const htmlContent = document.documentElement.outerHTML;
            console.log("HTML страницы:", htmlContent);

            // Пример: отправка HTML на сервер (замените URL)
            /*
            fetch('https://example.com/save-html', {
              method: 'POST',
              headers: {
                'Content-Type': 'text/html'
              },
              body: htmlContent
            })
            .then(response => {
              if (response.ok) {
                console.log('HTML успешно отправлен на сервер');
              } else {
                console.error('Ошибка отправки HTML:', response.status);
              }
            })
            .catch(error => {
              console.error('Ошибка отправки HTML:', error);
            });
            */


          }, 5000); // Задержка 5 секунд перед возвратом HTML (сейчас вызов просто логирует в консоль)
        }, 5000); // Задержка 5 секунд после первого скрипта
      }, 0); // Выполняем сразу

