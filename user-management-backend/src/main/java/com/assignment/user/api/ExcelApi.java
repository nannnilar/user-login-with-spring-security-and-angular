package com.assignment.user.api;

import com.assignment.user.entity.User;
import com.assignment.user.service.ExcelService;
import com.assignment.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/excel")
public class ExcelApi {

    private final ExcelService excelService;
    private final UserService userService;

    @GetMapping("/export/users")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportUsersToExcel(HttpServletResponse response) {
        try {
            String filename = "users.xlsx";
            List<User> products = userService.findAll();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//              response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
            response.setHeader("Content-Disposition", "attachment; filename=" +filename);

            excelService.exportUsersToExcel(products, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @PostMapping("/import/users")
    public ResponseEntity<String> importOrders(@RequestParam("file") MultipartFile file) {
        try {
            excelService.readUsersFromExcelFile(file);
            return ResponseEntity.ok("Orders imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import orders: " + e.getMessage());
        }
    }
}
