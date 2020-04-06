package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.common.ImageResizer;
import com.backend.bank.common.NewBlockSortPosition;
import com.backend.bank.converters.bases.converter.NewConverter;
import com.backend.bank.dto.request.NewsBlocksRequestDto;
import com.backend.bank.dto.response.news.*;
import com.backend.bank.dto.request.CategoryNewsRequest;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.*;
import com.backend.bank.repository.*;
import com.backend.bank.service.NewsService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class NewsServiceImpl implements NewsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsTranslationRepository newsTranslationRepository;

    @Autowired
    NewConverter newConverter;

    @Autowired
    NewsBlocksRepository newsBlocksRepository;

    @Autowired
    ImageResizer imageResizer;

    @Override
    public List<NewsReponseDto> findAllById(String local, List<Integer> ids) {
        return newConverter.converterListDto(local, newsRepository.findAllById(ids));

    }

    @Override
    public PaginationNews findAllByUser(String local, Integer page, Integer number) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.findAllByUser(pageable, userName);
        List<NewsReponseDto> newsResponseDtos = newConverter.converterListDto(local, pageAll.getContent());
        PaginationNews paginationNews = new PaginationNews();
        paginationNews.setNews(newsResponseDtos);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public PaginationNews findAllByIsActive(String local, Boolean isActive, Integer page, Integer number) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.findAllByIsActive(pageable, userName, isActive, local);
        List<NewsReponseDto> newsResponses = newConverter.converterListDto(local, pageAll.getContent());
        PaginationNews paginationNews = new PaginationNews();
        paginationNews.setNews(newsResponses);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public List<NewsReponseDto> findAllByIsActiveNotPagination(String local, Boolean isActive) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<News> newsList = newsRepository.findAllByIsActiveNotPagination(userName, isActive, local);
        if (newsList.size() != 0) {
            List<NewsReponseDto> newsResponses = newConverter.converterListDto(local, newsList);
            return newsResponses;
        }
        return null;
    }

    @Override
    public PaginationNews findAllPagination(String locale, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.getAllBy(pageable);
        List<NewsReponseDto> newsReponseDtos = newConverter.converterListDto(locale, pageAll.getContent());
        PaginationNews paginationNews = new PaginationNews();
        paginationNews.setNews(newsReponseDtos);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public List<NewsReponseDto> getNewsByCategoryIsActive(int idCategory, String local) {
        Optional<Category> category = categoryRepository.findById(idCategory);
        if (!category.isPresent()) {
            throw new NotFoundException("Category not found");
        }
        List<News> news = category.get().getNews();
        List<News> newsTemp = new ArrayList<>();

        if (news.size() > 0) {
            for (News newsElm : news) {
                Integer id = checkNewsTranslation(local, newsElm.getNewsTranslations());
                if (!newsElm.getNewsTranslations().get(id).getIsActive()) {
                    newsTemp.add(newsElm);
                }
            }
            news.removeAll(newsTemp);
            if (news.size() > 0) {
                return newConverter.converterListDto(local, news);
            }
        }
        return null;
    }

    @Override
    public NewsReponseDto getNewsById(int id, String local) {
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException("News not found");
        }
        return newConverter.converterDto(local, news.get());
    }

    @Override
    public NewsReponseDto addNews(NewDTO newDTO, String local) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userRepository.findByUserName(userName);
        if (!user.isPresent()) {
            throw new NotFoundException("Not User Found");
        }
        News news = newConverter.converterEntity(local, newDTO);
        news.setCreatedByUserId(user.get().getId());
        newsRepository.save(news);
        return newConverter.converterDto(local, news);
    }

    @Override
    public NewsReponseDto editNews(int id, NewDTO newDTO, String local) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userRepository.findByUserName(userName);
        if (!user.isPresent()) {
            throw new NotFoundException("Not User Found");
        }
        Constants.checkLocal(local);
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException("News not found");
        }
        Integer location = checkNewsTranslation(local, news.get().getNewsTranslations());
        news.get().getNewsTranslations().get(location).setTitle(newDTO.getTitle());
        news.get().getNewsTranslations().get(location).setLocale(local);
        news.get().getNewsTranslations().get(location).setIsActive(newDTO.getIs_active());
        news.get().getNewsTranslations().get(location).setDescription(newDTO.getDescription());
        news.get().getNewsTranslations().get(location).setShortDescription(newDTO.getShortDescription());
        news.get().getNewsTranslations().get(location).setMeta_description(newDTO.getMeta_description());
        news.get().getNewsTranslations().get(location).setMeta_keyword(newDTO.getMeta_keyword());
        news.get().getNewsTranslations().get(location).setMeta_title(newDTO.getMeta_title());
        news.get().getNewsTranslations().get(location).setUrl(Constants.toSlug(newDTO.getUrl()));
        Calendar calendar = Calendar.getInstance();
        news.get().setUpdatedAt(calendar);
        news.get().setCreatedAt(newDTO.getCreated_at());
        news.get().setEditByUserId(user.get().getId());
        news.get().setBase_image(newDTO.getBase_image());
        news.get().setMiniImage(newDTO.getMiniImage());
        news.get().setAuthor_name(newDTO.getAuthor_name());
        news.get().setIs_sticky(newDTO.getIs_sticky());
        news.get().getCategories().removeAll(news.get().getCategories());
        List<Category> categories = new ArrayList<>();
        for (CategoryNewsRequest categoryNewsRequest : newDTO.getCategories()) {
            Optional<Category> category = categoryRepository.findById(categoryNewsRequest.getId());
            if (!category.isPresent()) {
                throw new NotFoundException("Category not found");
            } else {
                categories.add(category.get());
            }
        }
        news.get().setCategories(categories);
        news.get().setNewsBlocks(newConverter.converterNewListNewsBlock2(local, news.get(), newDTO.getNewsBlocks()));
        newsRepository.save(news.get());

        return newConverter.converterDto(local, news.get());
    }

    @Override
    public void deleteNew(int id, String local) {
        Constants.checkLocal(local);
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException("News not found");
        }
        List<NewsTranslation> newsTranslations = newsTranslationRepository.findByNews(news.get());
        newsTranslationRepository.deleteInBatch(newsTranslations);
        newsRepository.delete(news.get());
    }

    @Override
    public PaginationNews findAllByTitle(String local, Integer page, Integer number, String search, Integer categoryId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if (page != 0) {
            page = page - 1;
        }
        String categoryId1 = categoryId == 0 ? "" : categoryId.toString();
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.findAllByTitle(pageable, userName, local, search, categoryId1);
        List<NewsReponseDto> newsResponse = newConverter.converterListDto(local, pageAll.getContent());
        PaginationNews paginationNews = new PaginationNews();
        paginationNews.setNews(newsResponse);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public PaginationNews findAllByCategoryId(String local, Integer page, Integer number, Integer categoryId) {
        Authentication authent = SecurityContextHolder.getContext().getAuthentication();
        String userName = authent.getName();
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.findAllByCategoryId(pageable, userName, local, categoryId);
        List<NewsReponseDto> newsResponse = newConverter.converterListDto(local, pageAll.getContent());
        PaginationNews paginationNews = new PaginationNews();
        paginationNews.setNews(newsResponse);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public PaginationHotNews findAllByCategorySlug(String local, Integer page, Integer number, String categorySlug) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.findAllByCategorySlug(pageable, local, categorySlug);
        Pageable hotNews = PageRequest.of(0, 6, sort);
        Page<News> pagesHotNews = newsRepository.findAllByCategorySlug(hotNews, local, categorySlug);
        List<NewsReponseDto> newsResponse = newConverter.converterListDto(local, pageAll.getContent());
        List<NewsReponseDto> hotNewsResponse = newConverter.converterListDto(local, pagesHotNews.getContent());
        PaginationHotNews paginationNews = new PaginationHotNews();
        paginationNews.setNews(newsResponse);
        paginationNews.setHotNews(hotNewsResponse);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public PaginationNews findAllByCategoryIdAndYear(String local, Integer page, Integer number, Integer categoryId, Integer year) {

        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<News> pageAll = newsRepository.findAllByCategoryIdAndYear(pageable, local, categoryId, year);
        List<NewsReponseDto> newsResponse = newConverter.converterListDto(local, pageAll.getContent());
        PaginationNews paginationNews = new PaginationNews();
        paginationNews.setNews(newsResponse);
        paginationNews.setSize(pageAll.getTotalPages());
        return paginationNews;
    }

    @Override
    public NewsReponseDto acceptNews(int id, String local) {
        Constants.checkLocal(local);
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new NotFoundException("News not found");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userRepository.findByUserName(userName);
        if (!user.isPresent()) {
            throw new NotFoundException("Not User Found");
        }
        Integer idNewsTranslation = checkNewsTranslation(local, news.get().getNewsTranslations());
        news.get().getNewsTranslations().get(idNewsTranslation).setIsActive(true);
        news.get().setApprovedByUserId(user.get().getId());
        newsRepository.save(news.get());
        return newConverter.converterDto(local, news.get());
    }

    @Override
    public List<NewsBlocksReponseDto> updatePositions(int idNew, int idNewBlock, int position, String local) {
        News news = newsRepository.findById(idNew).orElse(null);
        if (news == null) {
            throw new NotFoundException("New not found");
        }
        NewsBlocks newsBlock = newsBlocksRepository.findById(idNewBlock).orElse(null);
        if (newsBlock == null) {
            throw new NotFoundException("NewBlock not found");
        }
        List<NewsBlocks> newsBlocks = news.getNewsBlocks();
        Collections.sort(newsBlocks, new NewBlockSortPosition());

        int first_position = -1, last_position = -1;
        for (int i = 0; i <= newsBlocks.size() - 1; i++) {
            if (newsBlocks.get(i).getId() == newsBlock.getId()) {
                first_position = i;
            }
            if (newsBlocks.get(i).getPosition() == position) {
                last_position = i;
            }

        }
        if (first_position < last_position) {
            for (int i = first_position + 1; i <= last_position; i++) {
                newsBlocks.get(i).setPosition(newsBlocks.get(i - 1).getPosition());
            }
        } else {
            for (int i = last_position; i <= first_position - 1; i++) {
                newsBlocks.get(i).setPosition(newsBlocks.get(i + 1).getPosition());
            }
        }
        newsBlocks.get(first_position).setPosition(position);
        newsBlock.setPosition(position);
        newsBlocksRepository.save(newsBlock);
        Collections.sort(newsBlocks, new NewBlockSortPosition());
        return newConverter.converterListEntity(local, newsBlocks);
    }

    @Override
    public NewsReponseDto getNewsByUrl(String local, String name) {
        List<NewsTranslation> newsTranslation = newsTranslationRepository.findByUrlAndLocale(name, local);
        if (newsTranslation.size() == 0) {
            throw new NotFoundException("not found");
        }else {
            News news = newsTranslation.get(0).getNews();
            Integer idNewsTranslation = checkNewsTranslation(local, news.getNewsTranslations());
            if (news.getNewsTranslations().get(idNewsTranslation).getIsActive()) {
                return newConverter.converterDto(local, news);
            } else {
                return null;
            }
        }
    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            for (Integer id : ids
            ) {
                Optional<News> news = newsRepository.findById(id);
                if (!news.isPresent()) {
                    throw new NotFoundException("News not found");
                }
                List<NewsTranslation> newsTranslations = newsTranslationRepository.findByNews(news.get());
                newsTranslationRepository.deleteInBatch(newsTranslations);
                newsRepository.delete(news.get());
            }
            a = true;
        } catch (Exception e) {
            a = false;
        }
        return a;
    }

    @Override
    public void readExcelNews(String local) {
        String path = "https://sapotacorp.com:8443/uploads";
        FileInputStream file = null;
        List<NewDTO> newsRequest = new ArrayList<>();
        try {

            file = new FileInputStream(new File("TB_Content.xlsx"));
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            // Get all rows
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                if (nextRow.getRowNum() == 0) {
                    continue;
                }
                // Get all cells
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                // Read cells and set value for book object
                NewDTO newsRequestDto = new NewDTO();
                List<CategoryNewsRequest> categoryRequests = new ArrayList<>();
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
                        case 0:
                            newsRequestDto.setId(new BigDecimal((double) cellValue).intValue());
                            break;
                        case 2:
                            newsRequestDto.setTitle((String) getCellValue(cell));
                            newsRequestDto.setIs_sticky(true);
                            newsRequestDto.setIs_active(true);
                            newsRequestDto.setMeta_description((String) getCellValue(cell));
                            newsRequestDto.setMeta_title((String) getCellValue(cell));
                            newsRequestDto.setMeta_keyword((String) getCellValue(cell));
                            break;
                        case 3:
                            newsRequestDto.setUrl((String) getCellValue(cell));
                            break;
                        case 5:
                            newsRequestDto.setShortDescription((String) getCellValue(cell));
                            break;
                        case 6:
                            newsRequestDto.setDescription((String) getCellValue(cell));
                            break;
                        case 8:
                            CategoryNewsRequest categoryNewsRequest = new CategoryNewsRequest();
                            categoryNewsRequest.setId(new BigDecimal((double) cellValue).intValue());
                            categoryRequests.add(categoryNewsRequest);
                            break;
                        case 10:
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Calendar calendar = Calendar.getInstance();
                            String dateString = dateFormat.format(cell.getDateCellValue());
                            try {
                                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
                                calendar.setTime(date);
                                newsRequestDto.setCreated_at(calendar);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;

                        case 16:
                            newsRequestDto.setBase_image(path + (String) getCellValue(cell));
                            break;

                        default:
                            break;
                    }
                    newsRequestDto.setCategories(categoryRequests);
                    NewsBlocksRequestDto newsBlock = new NewsBlocksRequestDto();
                    newsBlock.setContent(".");
                    newsBlock.setPosition(1);
                    newsBlock.setId_block(1);
                    newsBlock.setTitle(".");
                    List<NewsBlocksRequestDto> newsBlocksRequestDtos = new ArrayList<>();
                    newsRequestDto.setNewsBlocks(newsBlocksRequestDtos);
                }
                newsRequest.add(newsRequestDto);
            }
            workbook.close();
            file.close();

            for (NewDTO newDTO: newsRequest
                 ) {
                addNews(newDTO, local);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public Integer checkNewsTranslation(String local, List<NewsTranslation> newsTranslations) {
        Integer size = newsTranslations.size();
        if (size == 0) {
            throw new NotFoundException("Not Found News");
        }
        for (Integer i = 0; i < size; i++) {
            if (local.equalsIgnoreCase(newsTranslations.get(i).getLocale())) {
                return i;
            }
        }
        return -1;
    }

}