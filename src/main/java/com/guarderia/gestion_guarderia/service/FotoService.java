package com.guarderia.gestion_guarderia.service;

import com.guarderia.gestion_guarderia.dto.FotoDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
/*
@Service
public class FotoService {

    private final Path rootLocation= Paths.get("uploads");
    @Autowired
    private FotoRepository fotoRepository;
    @Autowired
    private ActividadRepository actividadRepository;
    @Autowired
    private FileStorageService fileStorageService;

    private final String subfolder="fotos";
    private final String subfolderActividad="actividades";




    public Foto saveFoto(MultipartFile file, Long actividadId)  throws IOException{
        //Verificar si la actividad existe
        Actividad actividad = actividadRepository.findById(actividadId).orElseThrow(() -> new NotFoundExeption("Actividad no encontrada con id " + actividadId));
        String path = fileStorageService.storeFile(file,subfolder+"/"+subfolderActividad+"/"+actividadId);
        Foto foto = new Foto();
        foto.setUrl(path);
        foto.setActividad(actividad);
        foto.setFecha(new Date());
        return fotoRepository.save(foto);
    }

    public List<Foto> getFotosByActividadId(Long actividadId){
        return fotoRepository.findByActividadId(actividadId);
    }

    public Optional<Foto> getFotoById(Long id){
        return fotoRepository.findById(id);
    }

    public void deleteFoto(Long idFoto) throws IOException{
        Foto foto = fotoRepository.findById(idFoto).orElseThrow(()->new NotFoundExeption("Foto no encontrada con id "+idFoto));
        fileStorageService.deleteFile(foto.getUrl());
        fotoRepository.deleteById(idFoto);
    }

}



 */

public interface FotoService {

        FotoDTO saveFoto(MultipartFile file, Long actividadId) throws IOException;

        List<Resource> getFotosByActividadIdResource(Long actividadId) throws IOException;

        Resource getFotoByIdResource(Long id) throws IOException;

        void deleteFoto(Long idFoto) throws IOException;


}