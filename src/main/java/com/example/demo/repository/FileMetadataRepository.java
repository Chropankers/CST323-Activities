public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {
    Optional<FileMetadata> findByUserIdAndFileName(Long userId, String fileName);
}