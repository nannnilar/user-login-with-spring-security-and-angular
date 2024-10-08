package com.assignment.user.service;

import com.assignment.user.dto.RegistrationDto;
import com.assignment.user.entity.User;
import com.assignment.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelService {
    private final UserService userService;

    public void exportUsersToExcel(List<User> users, OutputStream outputStream) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Login ID", "Password", "Comfirm Password", "Name","Email", "Address", "Role Id", "Role Type", "Role Name"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            // Create data rows
            int rowNum = 1;
            for (User user : users) {
                for (UserRole userRole : user.getRoles()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(user.getLoginId());
                    row.createCell(1).setCellValue(user.getPassword());
                    row.createCell(2).setCellValue(user.getPassword());
                    row.createCell(3).setCellValue(user.getName());
                    row.createCell(4).setCellValue(user.getEmail());
                    row.createCell(5).setCellValue(user.getAddress());
                    row.createCell(6).setCellValue(user.getRoleId());
                    row.createCell(7).setCellValue(userRole.getType().name());
                    row.createCell(8).setCellValue(userRole.getName());

                    System.out.println(" "+ row);
                }
            }
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(outputStream);
        }
    }

    @Transactional
    public List<RegistrationDto> readUsersFromExcelFile(MultipartFile file) throws IOException {
        List<RegistrationDto> users = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row currentRow : sheet) {
                if (currentRow.getRowNum() == 0) {
                    // Skip header row
                    continue;
                }

                RegistrationDto user = new RegistrationDto();

//                product.setId((long) currentRow.getCell(0).getNumericCellValue());
                user.setLoginId(currentRow.getCell(0).getStringCellValue());
                user.setPassword(currentRow.getCell(1).getStringCellValue());
                user.setComfirmPassword(currentRow.getCell(2).getStringCellValue());
                user.setName(currentRow.getCell(3).getStringCellValue());
                user.setEmail(currentRow.getCell(4).getStringCellValue());
                user.setAddress(currentRow.getCell(5).getStringCellValue());
                user.setRoleId((int) currentRow.getCell(6).getNumericCellValue());

                users.add(user);
                userService.saveUser(user);
            }

            workbook.close();
        }

        return users;
    }



}
