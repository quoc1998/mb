package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.SliderConverter;
import com.backend.bank.dto.request.SliderRequestDto;
import com.backend.bank.dto.response.slider.SliderReponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.SliderSlides;
import com.backend.bank.model.Sliders;
import com.backend.bank.repository.SliderSlidesRepository;
import com.backend.bank.repository.SlidersRepository;
import com.backend.bank.service.SlidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderServiceImpl implements SlidersService {
    @Autowired
    SlidersRepository slidersRepository;

    @Autowired
    SliderSlidesRepository sliderSlidesRepository;

    @Autowired
    SliderConverter sliderConverter;

    @Override
    public List<SliderReponseDto> findAll(String local) {
        List<Sliders> sliders = slidersRepository.findAll();
        List<SliderReponseDto> sliderReponseDtos = sliderConverter.sliderReponseDtosConverter(sliders, local);
        return sliderReponseDtos;
    }

    @Override
    public SliderReponseDto findById(Integer id, String local) {
        SliderReponseDto sliderReponseDto = sliderConverter.SliderConverter(slidersRepository.findById(id).get(), local);
        return sliderReponseDto;
    }

    @Override
    public SliderReponseDto addSlider(SliderRequestDto sliderRequestDto, String local) {
        SliderReponseDto sliderReponseDto = new SliderReponseDto();
        try {
            Sliders sliders = sliderConverter.SliderConverterEntity(sliderRequestDto, local);
            slidersRepository.save(sliders);
            sliderReponseDto = sliderConverter.SliderConverter(sliders, local);
        } catch (Exception e) {
            e.printStackTrace();
            sliderReponseDto = null;
        }

        return sliderReponseDto;
    }

    @Override
    public SliderReponseDto editSlider(Integer id, SliderRequestDto sliderRequestDto, String local) {
        SliderReponseDto sliderReponseDto = new SliderReponseDto();
        Sliders sliders = slidersRepository.findById(id).orElse(null);
        if (sliders == null) {
            throw new NotFoundException("Slider not found");
        }
        try {

            sliders = sliderConverter.SliderSetEntity(sliders, sliderRequestDto, local);
            slidersRepository.save(sliders);
            sliderReponseDto = sliderConverter.SliderConverter(sliders, local);
        } catch (Exception e) {
            e.printStackTrace();
            sliderReponseDto = null;
        }

        return sliderReponseDto;
    }


    @Override
    public void deleteSliderById(Integer id) {
        try {
            Sliders slider = slidersRepository.findById(id).orElse(null);
            if (slider == null) {
                throw new NotFoundException("Not Found Slide");
            }
            slidersRepository.delete(slider);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteSlidesliderById(Integer id) {
        try {
            SliderSlides sliderSlides = sliderSlidesRepository.findById(id).orElse(null);
            if (sliderSlides == null) {
                throw new NotFoundException("Not found SliderSlide");
            } else {
                sliderSlidesRepository.delete(sliderSlides);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            for (Integer id : ids
            ) {
                try {
                    Sliders slider = slidersRepository.findById(id).orElse(null);
                    if (slider == null) {
                        throw new NotFoundException("Not Found Slide");
                    }
                    slidersRepository.delete(slider);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            a = true;
        } catch (Exception e) {
            a = false;
        }
        return a;
    }


}
