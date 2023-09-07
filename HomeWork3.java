/*
 * Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:
 * Фамилия Имя Отчество номер телефона
 * Форматы данных:
 * фамилия, имя, отчество - строки
 * номертелефона - целое беззнаковое число без форматирования
 * Ввод всех элементов через пробел
 * Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, обработать его и показать пользователю сообщение, что он 
 * ввел меньше и больше данных, чем требуется.
 * Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не совпадают, нужно бросить исключение, соответствующее 
 * типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно. 
 * Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
 * <Фамилия><Имя><Отчество><номер_телефона>
 * Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
 * Не забудьте закрыть соединение с файлом.
 * При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки. Третий вид ошибки
 */
 
 
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class HomeWork3 {

    public static void main(String[] args) {
        System.out.println("Введите Фамилию Имя Отчество (Латиницей) Номер_Телефона через пробел");
        try (DataWriter datawriter1 = new DataWriter()) {
                String[] array1 = datawriter1.scanInfo();
                String filename = array1[0];
                String filedata = datawriter1.parseData(array1);
                datawriter1.dataToFile(filedata, filename);
            }

        catch (Exception e) {
            System.out.println("Ошибка при записи файла" + e.getMessage());
        }
    }
}
    class DataWriter implements AutoCloseable {
        private int telephone;
        public DataWriter() {
            this.telephone = telephone;
        }

        public String[] scanInfo() throws WrongAmountData {
            Scanner sc = new Scanner(System.in);
            String inputData = sc.nextLine();
            String[] data = inputData.split(" ");
            sc.close();
            if (data.length != 4) {
                throw new WrongAmountData(" Вы ввели меньше или больше данных, чем требуется");
            }
            return data;
        }

        public String parseData(String[] data) throws WrongFormat {
            
            if (!data[0].matches("^[a-zA-Z]*$")) {
                throw new WrongFormat(" Фамилия введена неверно, используйте латинский алфивит");
            }
            if (!data[1].matches("^[a-zA-Z]*$")) {
                throw new WrongFormat(" Имя введено неверно, используйте латинский алфивит");
            }
            if (!data[2].matches("^[a-zA-Z]*$")) {
                throw new WrongFormat(" Отчество введено неверно, используйте латинский алфивит");
            }
            try {
                telephone = Integer.parseInt(data[3]);
            } catch (Exception e) {
                throw new WrongFormat(" Телефон нужно вводить цифрами");
                // TODO: переделать, меня это смущает
            }

            String text = String.format("<%s> <%s> <%s> <%s>", data[0], data[1], data[2], data[3]);
            return text;
        }

        public void dataToFile(String filedata, String filename) throws FileProblem {
            File file = new File(filename+".txt");
            try (FileWriter writer = new FileWriter(file, true)) {
                if (file.length()!=0){
                    
                    writer.append('\n');}
                
                writer.write(filedata);
                // запись по символам
                writer.flush();
            }
            catch (Exception e) {
            throw new FileProblem("Невозможно записать файл");
            }
        }

        @Override
        public void close() throws Exception {
            // Закрываем коробку
        }
    }

class WrongAmountData extends Exception {
    public WrongAmountData(String s) {
        super(s);
    }
}

class WrongFormat extends Exception {
    public WrongFormat(String s) {
        super(s);
    }
}

class FileProblem extends Exception {
    public FileProblem(String s) {
        super(s);
    }
}
