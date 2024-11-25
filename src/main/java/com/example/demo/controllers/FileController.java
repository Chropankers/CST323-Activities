@RestController
@RequestMapping("/files")
public class FileController {

    private final WasabiStorageService wasabiStorageService;

    @Autowired
    public FileController(WasabiStorageService wasabiStorageService) {
        this.wasabiStorageService = wasabiStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("userId") Long userId,
                                             @RequestParam("serverId") Long serverId) {
        try {
            String fileUrl = wasabiStorageService.uploadFile(file, userId, serverId);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }

    @GetMapping("/{userId}/{fileName}")
    public void downloadFile(@PathVariable Long userId,
                             @PathVariable String fileName,
                             HttpServletResponse response) {
        try {
            S3Object s3Object = wasabiStorageService.downloadFile(userId, fileName);
            response.setContentType(s3Object.getObjectMetadata().getContentType());
            response.setContentLengthLong(s3Object.getObjectMetadata().getContentLength());
            IOUtils.copy(s3Object.getObjectContent(), response.getOutputStream());
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}
