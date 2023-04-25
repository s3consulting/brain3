package util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileObject {
    BufferedWriter bw;
    FileOutputStream fos;
}
