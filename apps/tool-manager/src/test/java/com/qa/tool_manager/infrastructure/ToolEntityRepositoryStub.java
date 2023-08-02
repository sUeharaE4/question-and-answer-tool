// package com.qa.tool_manager.infrastructure;

// import com.qa.common_libs.generator.DatetimeGenerator;
// import com.qa.tool_manager.domain.tool.model.ToolEntity;
// import com.qa.tool_manager.domain.tool.model.ToolEntityRepository;
// import com.qa.tool_manager.domain.tool.model.vo.ToolDescriptionValue;
// import com.qa.tool_manager.domain.tool.model.vo.ToolNameValue;
// import org.springframework.data.domain.Example;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Objects;
// import java.util.Optional;
// import java.util.function.Function;
// import java.util.stream.Collectors;

// public class ToolEntityRepositoryStub implements ToolEntityRepository {

// private List<ToolEntity> records;

// private final DatetimeGenerator datetimeGenerator;

// public ToolEntityRepositoryStub(List<ToolEntity> entities, DatetimeGenerator generator) {
// this.records = entities;
// this.datetimeGenerator = generator;
// }

// public ToolEntityRepositoryStub() {
// this.records = new ArrayList<>();
// ToolNameValue name = new ToolNameValue(String.format("name_%05d", 1));
// ToolDescriptionValue description =
// new ToolDescriptionValue(String.format("description_%05d", 1));
// }

// @Override
// public void deleteAllByIdInBatch(Iterable<Long> ids) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteAllByIdInBatch'");
// }

// @Override
// public void deleteAllInBatch() {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
// }

// @Override
// public void deleteAllInBatch(Iterable<ToolEntity> entities) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteAllInBatch'");
// }

// @Override
// public <S extends ToolEntity> List<S> findAll(Example<S> example) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAll'");
// }

// @Override
// public <S extends ToolEntity> List<S> findAll(Example<S> example, Sort sort) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAll'");
// }

// @Override
// public void flush() {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'flush'");
// }

// @Override
// public ToolEntity getById(Long arg0) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'getById'");
// }

// @Override
// public ToolEntity getOne(Long arg0) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'getOne'");
// }

// @Override
// public ToolEntity getReferenceById(Long id) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'getReferenceById'");
// }

// @Override
// public <S extends ToolEntity> List<S> saveAllAndFlush(Iterable<S> entities) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'saveAllAndFlush'");
// }

// @Override
// public <S extends ToolEntity> S saveAndFlush(S entity) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'saveAndFlush'");
// }

// @Override
// public <S extends ToolEntity> List<S> saveAll(Iterable<S> entities) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
// }

// @Override
// public List<ToolEntity> findAll() {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAll'");
// }

// @Override
// public List<ToolEntity> findAllById(Iterable<Long> ids) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAllById'");
// }

// @Override
// public <S extends ToolEntity> S save(S entity) {
// LocalDateTime currentTime = datetimeGenerator.getCurrentDateTime();
// S saveEntity = entity;
// if (Objects.nonNull(entity.getId())) {
// saveEntity = (S) filterById(entity.getId()).orElse(entity);
// } else {
// saveEntity.setId((long) (records.size() + 1));
// }
// saveEntity.setName(entity.getName());
// saveEntity.setDescription(entity.getDescription());
// if (Objects.isNull(saveEntity.getCreatedAt())) {
// saveEntity.setCreatedAt(currentTime);
// }
// saveEntity.setUpdatedAt(currentTime);
// records.removeIf(r -> r.getId().equals(saveEntity.getId()));
// records.add(saveEntity);
// records.stream().sorted(Comparator.comparing(ToolEntity::getId))
// .collect(Collectors.toList());
// return saveEntity;
// }

// @Override
// public Optional<ToolEntity> findById(Long id) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findById'");
// }

// @Override
// public boolean existsById(Long id) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'existsById'");
// }

// @Override
// public long count() {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'count'");
// }

// @Override
// public void deleteById(Long id) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
// }

// @Override
// public void delete(ToolEntity entity) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'delete'");
// }

// @Override
// public void deleteAllById(Iterable<? extends Long> ids) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteAllById'");
// }

// @Override
// public void deleteAll(Iterable<? extends ToolEntity> entities) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
// }

// @Override
// public void deleteAll() {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
// }

// @Override
// public List<ToolEntity> findAll(Sort sort) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAll'");
// }

// @Override
// public Page<ToolEntity> findAll(Pageable pageable) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAll'");
// }

// @Override
// public <S extends ToolEntity> Optional<S> findOne(Example<S> example) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findOne'");
// }

// @Override
// public <S extends ToolEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findAll'");
// }

// @Override
// public <S extends ToolEntity> long count(Example<S> example) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'count'");
// }

// @Override
// public <S extends ToolEntity> boolean exists(Example<S> example) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'exists'");
// }

// @Override
// public <S extends ToolEntity, R> R findBy(Example<S> example,
// Function<FetchableFluentQuery<S>, R> queryFunction) {
// // TODO Auto-generated method stub
// throw new UnsupportedOperationException("Unimplemented method 'findBy'");
// }

// private Optional<ToolEntity> filterById(Long id) {
// return records.stream().filter(r -> r.getId().equals(id)).findFirst();
// }


// }
