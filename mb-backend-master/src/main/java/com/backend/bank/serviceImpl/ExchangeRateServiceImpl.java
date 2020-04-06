package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.converters.bases.converter.ExchangeRateConverter;
import com.backend.bank.dto.request.CategoryNewsRequest;
import com.backend.bank.dto.request.ExchangeRateDetailRequestDto;
import com.backend.bank.dto.request.ExchangeRateRequestDto;
import com.backend.bank.dto.request.NewsBlocksRequestDto;
import com.backend.bank.dto.response.news.NewDTO;
import com.backend.bank.dto.response.toolbar.ExchangeRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationExchangeRate;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.ExchangeRate;
import com.backend.bank.model.ExchangeRateDetail;
import com.backend.bank.repository.ExchangeRateDetailRepository;
import com.backend.bank.repository.ExchangeRateRepository;
import com.backend.bank.service.ExchangeRateService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeRateDetailRepository exchangeRateDetailRepository;

    @Autowired
    private ExchangeRateConverter exchangeRateConverter;

    @Override
    public List<ExchangeRateReponseDto> getAll(String locale) {
        Constants.checkLocal(locale);
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();
        List<ExchangeRateReponseDto> exchangeRateReponseDtos = exchangeRateConverter.convertListExchangeRateToDto(exchangeRates);
        return exchangeRateReponseDtos;
    }

    @Override
    public PaginationExchangeRate findAllPagin(String locale, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<ExchangeRate> pageAll = exchangeRateRepository.getAllBy(pageable);
        List<ExchangeRateReponseDto> exchangeRateReponseDtos = exchangeRateConverter.convertListExchangeRateToDto(pageAll.getContent());
        PaginationExchangeRate paginationExchangeRate = new PaginationExchangeRate();
        paginationExchangeRate.setExchangeRates(exchangeRateReponseDtos);
        paginationExchangeRate.setSize(pageAll.getTotalPages());
        return paginationExchangeRate;
    }

    @Override
    public PaginationExchangeRate findRangeDate(String locale, Integer page, Integer number, String dateStart, String dateStop) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<ExchangeRate> pageAll = exchangeRateRepository.findRangeDate(pageable, dateStart, dateStop);
        if (pageAll.getContent().size() > 0) {
            List<ExchangeRateReponseDto> exchangeRateReponseDtos = exchangeRateConverter.convertListExchangeRateToDto(pageAll.getContent());
            PaginationExchangeRate paginationExchangeRate = new PaginationExchangeRate();
            paginationExchangeRate.setExchangeRates(exchangeRateReponseDtos);
            paginationExchangeRate.setSize(pageAll.getTotalPages());
            return paginationExchangeRate;
        }
        return null;

    }

    @Override
    public ExchangeRateReponseDto addExchangeRate(String locale, ExchangeRateRequestDto exchangeRateRequestDto) {
        Constants.checkLocal(locale);
        ExchangeRateReponseDto exchangeRateReponseDto;
        try {
            ExchangeRate exchangeRate = exchangeRateConverter.convertDtoToEntity(exchangeRateRequestDto);
            exchangeRateRepository.save(exchangeRate);
            exchangeRateReponseDto = exchangeRateConverter.convertExchangeRateToDto(exchangeRate);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return exchangeRateReponseDto;
    }

    @Override
    public ExchangeRateReponseDto getExchangeRate(String date) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByDate(date);
        if (exchangeRate == null) {
            throw new NotFoundException("Not Found Exchange Rate");
        }
        return exchangeRateConverter.convertExchangeRateToDto(exchangeRate);
    }

    @Override
    public ExchangeRateReponseDto getExchangeRateNew() {
        ExchangeRate exchangeRate = exchangeRateRepository.findByDateNew();
        if (exchangeRate == null) {
            throw new NotFoundException("Not Found Exchange Rate");
        }
        return exchangeRateConverter.convertExchangeRateToDto(exchangeRate);
    }

    @Override
    public ExchangeRateReponseDto getExchangeRate(String locale, Integer id) {
        Constants.checkLocal(locale);
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(id);
        if (!exchangeRate.isPresent()) {
            throw new NotFoundException("InterestRate not found!!");
        }
        return exchangeRateConverter.convertExchangeRateToDto(exchangeRate.get());
    }

    @Override
    public ExchangeRateReponseDto editExchangeRate(String locale, Integer id, ExchangeRateRequestDto exchangeRateRequestDto) {
        Constants.checkLocal(locale);
        ExchangeRateReponseDto exchangeRateReponseDto;
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(id);
        if (!exchangeRate.isPresent()) {
            throw new NotFoundException("InterestRate not found!!");
        }
        try {
            ExchangeRate exchangeRate1 = exchangeRateConverter.convertDtoToEntityEdit(exchangeRate.get(), exchangeRateRequestDto);
            exchangeRateRepository.save(exchangeRate1);
            exchangeRateReponseDto = exchangeRateConverter.convertExchangeRateToDto(exchangeRate1);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return exchangeRateReponseDto;
    }

    @Override
    public void deleteExchangeRate(String locale, Integer id) {
        Constants.checkLocal(locale);
        Optional<ExchangeRate> exchangeRate = exchangeRateRepository.findById(id);
        if (!exchangeRate.isPresent()) {
            throw new NotFoundException("InterestRate not found!!");
        }
        List<ExchangeRateDetail> exchangeRateDetailList = exchangeRateDetailRepository.findByExchangeRate(exchangeRate.get());
        exchangeRateDetailRepository.deleteInBatch(exchangeRateDetailList);
        exchangeRateRepository.delete(exchangeRate.get());
    }

    @Override
    public ExchangeRateReponseDto filterDateUpdate(String locale, ExchangeRateRequestDto exchangeRateRequestDto) {
        Constants.checkLocal(locale);

        return null;
    }
    @Override
    public void importExchangeRate(String local){
        FileInputStream file = null;
        List<ExchangeRateRequestDto> exchangeRateRequestDtos = new ArrayList<>();
        try {

            file = new FileInputStream(new File("TB_ExchangeRate.xlsx"));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            //cứ 19 dòng thì tạo được 1 a
            ExchangeRateRequestDto exchangeRateRequestDto = null;
            List<ExchangeRateDetailRequestDto> exchangeRateDetail = null;
            int i = 0;
            while (iterator.hasNext()) {
                restart://trong 1 hàng
                if (i == 0){
                    exchangeRateRequestDto = new ExchangeRateRequestDto();
                    exchangeRateDetail = new ArrayList<>();

                }
                ExchangeRateDetailRequestDto exchangeRateDetailRequestDto = new ExchangeRateDetailRequestDto();
                if (i < 18){
                    Row nextRow = iterator.next();
                    if (nextRow.getRowNum() == 0) {
                        continue;
                    }
                    // Get all cells
                    Iterator<Cell> cellIterator = nextRow.cellIterator();
                    // Read cells and set value for book object
                    //ExchangeRateDetailRequestDto exchangeRateDetailRequestDto = new ExchangeRateDetailRequestDto();
                    while (cellIterator.hasNext()) {
                        //Read cell
                        Cell cell = cellIterator.next();
                        Object cellValue = getCellValue(cell);
                        if (cellValue == null || cellValue.toString().isEmpty()) {
                            continue;
                        }
                        // Set value for book object
                        int columnIndex = cell.getColumnIndex();
                        switch (columnIndex) {
                            case 1:
                                exchangeRateDetailRequestDto.setCurrency(getExchangeRate(new BigDecimal((double) cellValue).intValue()));
                                break;
                            case 2:
                                exchangeRateDetailRequestDto.setBuy_cash(new BigDecimal((double) cellValue).doubleValue());
                                break;
                            case 3:
                                exchangeRateDetailRequestDto.setBuy_transfer(new BigDecimal((double) cellValue).doubleValue());
                                break;
                            case 4:
                                exchangeRateDetailRequestDto.setSell(new BigDecimal((double) cellValue).doubleValue());
                                break;
                            case 5:
                                exchangeRateDetailRequestDto.setChange_USD(new BigDecimal((double) cellValue).doubleValue());
                                break;
                            case 6:
                                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar calendar1 = Calendar.getInstance();
                                String dateString1 = dateFormat1.format(cell.getDateCellValue());
                                try {
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString1);
                                    calendar1.setTime(date);
                                    exchangeRateRequestDto.setDate_update(calendar1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 7:
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Calendar calendar = Calendar.getInstance();
                                String dateString = dateFormat.format(cell.getDateCellValue());
                                try {
                                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                                    calendar.setTime(date);
                                    exchangeRateRequestDto.setCreatedAt(calendar);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    exchangeRateDetail.add(exchangeRateDetailRequestDto);
                    i = i+1;
                }else {
                    i = 0;
                    exchangeRateRequestDto.setExchangeRateDetail(exchangeRateDetail);
                    exchangeRateRequestDtos.add(exchangeRateRequestDto);

                }
            }
            workbook.close();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };

    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
    public static String getExchangeRate(Integer idExchangeRate){
        String exchangeRate = "";
        switch (idExchangeRate){
            case 1:
                exchangeRate = "USD";
                break;
            case 2:
                exchangeRate = "USD_20";
                break;
            case 3:
                exchangeRate = "USD_5";
                break;
            case 4:
                exchangeRate = "EUR";
            break;
            case 5:
                exchangeRate = "GBP";
            break;
            case 6:
                exchangeRate = "JPY";
            break;
            case 7:
                exchangeRate = "HKD";
            break;
            case 8:
                exchangeRate = "CNY";
            break;
            case 9:
                exchangeRate = "AUD";
            break;
            case 10:
                exchangeRate = "CAD";
            break;
            case 11:
                exchangeRate = "SGD";
            break;
            case 12:
                exchangeRate = "THB";
            break;
            case 13:
                exchangeRate = "CHF";
            break;
            case 14:
                exchangeRate = "SEK";
            break;
            case 15:
                exchangeRate = "LAK";
            break;
            case 16:
                exchangeRate = "KHR";
            break;
            case 17:
                exchangeRate = "RUB";
            break;
            case 18:
                exchangeRate = "KRW";
            break;
            case 19:
                exchangeRate = "NZD";
            break;
            default:
                exchangeRate = "USD";
        }
        return exchangeRate;
    }
}
