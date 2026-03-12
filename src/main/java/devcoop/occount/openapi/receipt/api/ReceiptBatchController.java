package devcoop.occount.openapi.receipt.api;

import devcoop.occount.openapi.receipt.service.ReceiptBatchQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "영수증")
@RestController
@RequestMapping("/api/v1")
public class ReceiptBatchController {

    private final ReceiptBatchQueryService receiptBatchQueryService;

    public ReceiptBatchController(ReceiptBatchQueryService receiptBatchQueryService) {
        this.receiptBatchQueryService = receiptBatchQueryService;
    }

    @Operation(summary = "키오스크 영수증을 페이지 단위 또는 전체로 조회합니다")
    @GetMapping("/receipts")
    public List<ReceiptBatchResponse> getReceipts(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "키오스크 영수증 판매일 조회 시작일입니다. 입력한 날짜를 포함합니다.")
            @RequestParam(required = false) LocalDate saleDateFrom,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "키오스크 영수증 판매일 조회 종료일입니다. 입력한 날짜를 포함합니다.")
            @RequestParam(required = false) LocalDate saleDateTo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "saleDate,desc") String sort
    ) {
        return receiptBatchQueryService.getReceipts(saleDateFrom, saleDateTo, page, size, sort);
    }
}
