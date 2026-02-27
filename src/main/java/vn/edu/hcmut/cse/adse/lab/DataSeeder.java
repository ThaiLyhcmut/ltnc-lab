package vn.edu.hcmut.cse.adse.lab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.repository.StudentRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public DataSeeder(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        if (studentRepository.count() > 0) {
            return;
        }

        String[][] data = {
            {"Nguyen Van A", "nva@hcmut.edu.vn", "20"},
            {"Tran Thi B", "ttb@hcmut.edu.vn", "21"},
            {"Le Van C", "lvc@hcmut.edu.vn", "22"},
            {"Pham Thi D", "ptd@hcmut.edu.vn", "19"},
            {"Hoang Van E", "hve@hcmut.edu.vn", "23"},
            {"Vo Thi F", "vtf@hcmut.edu.vn", "20"},
            {"Dang Van G", "dvg@hcmut.edu.vn", "21"},
            {"Bui Thi H", "bth@hcmut.edu.vn", "22"},
            {"Do Van I", "dvi@hcmut.edu.vn", "24"},
            {"Ngo Thi K", "ntk@hcmut.edu.vn", "19"},
            {"Duong Van L", "dvl@hcmut.edu.vn", "20"},
            {"Ly Thi M", "ltm@hcmut.edu.vn", "21"},
            {"Truong Van N", "tvn@hcmut.edu.vn", "23"},
            {"Mai Thi O", "mto@hcmut.edu.vn", "22"},
            {"Ha Van P", "hvp@hcmut.edu.vn", "20"},
            {"Dinh Thi Q", "dtq@hcmut.edu.vn", "19"},
            {"Cao Van R", "cvr@hcmut.edu.vn", "24"},
            {"Luu Thi S", "lts@hcmut.edu.vn", "21"},
            {"Trinh Van T", "tvt@hcmut.edu.vn", "22"},
            {"Vu Thi U", "vtu@hcmut.edu.vn", "20"},
            {"Huynh Van V", "hvv@hcmut.edu.vn", "23"},
            {"Phan Thi X", "ptx@hcmut.edu.vn", "19"},
            {"Luong Van Y", "lvy@hcmut.edu.vn", "21"},
            {"Ta Thi Z", "ttz@hcmut.edu.vn", "22"},
            {"Nguyen Minh Tuan", "nmt@gmail.com", "20"},
            {"Tran Hoang Nam", "thn@gmail.com", "21"},
            {"Le Thi Lan", "ltl@gmail.com", "19"},
            {"Pham Duc Manh", "pdm@gmail.com", "23"},
            {"Hoang Thi Mai", "htm@gmail.com", "22"},
            {"Vo Quoc Dat", "vqd@gmail.com", "24"},
        };

        for (String[] s : data) {
            studentRepository.save(new Student(null, s[0], s[1], Integer.parseInt(s[2])));
        }

        System.out.println("Seeded " + data.length + " students.");
    }
}
