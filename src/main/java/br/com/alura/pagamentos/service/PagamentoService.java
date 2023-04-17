package br.com.alura.pagamentos.service;

import br.com.alura.pagamentos.dto.PagamentoDto;
import br.com.alura.pagamentos.model.Pagamento;
import br.com.alura.pagamentos.model.Status;
import br.com.alura.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * metodo para listar todos os pagamento
     *
     * @return List<PagamentoDto>
     */

    public Page<PagamentoDto> obterTodos(Pageable paginacao) {
        return repository.findAll(paginacao).map(p -> modelMapper.map(p, PagamentoDto.class));
    }

    /**
     * metodo para obter pagamentos por id
     *
     * @param id
     * @return PagamentoDto
     */

    public PagamentoDto obterPorId(Long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    /**
     * metodo para criar um pagamento
     *
     * @param dto
     * @return PagamentoDto
     */

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    /**
     * metodo para atualizar um pagamento
     *
     * @param id
     * @param dto
     * @return PagamentoDto
     */

    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto) {
        Pagamento pagamento = modelMapper.map(dto, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDto.class);
    }

    /**
     * metodo para deletar um pagamento
     *
     * @param id
     */
    public void deletarPagamento(Long id) {
        repository.deleteById(id);
    }

}
