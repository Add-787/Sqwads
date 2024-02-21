using System.Transactions;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;
using Sqwads.Application.Interfaces.Data;
using Sqwads.Infrastructure.Data.Repos;

namespace Sqwads.Infrastructure.Data;

internal class UnitOfWork : IUnitOfWork
{
    private readonly AppDbContext _context;
    private IDbContextTransaction? _currentTransaction;

    public SquadRepo Squads { get;  }
    public bool HasActiveTransaction => _currentTransaction is not null;

    public UnitOfWork(AppDbContext context, SquadRepo squads) {
        _context = context;
        Squads = squads;

    }

    public async Task BeginTransactionAsync()
    {
        if(_currentTransaction is not null)
        {
            throw new InvalidOperationException("A transaction already in porogress.");
        }
        _currentTransaction = await _context.Database.BeginTransactionAsync();
    }

    public async Task CommitTransactionAsync()
    {
        try {
            await _context.SaveChangesAsync();
            _currentTransaction?.Commit();
        } catch
        {
            await RollbackTransactionAsync();
            throw;
        }
        finally
        {
            if(_currentTransaction is not null)
            {
                await _currentTransaction.DisposeAsync();
                _currentTransaction = null;
            }

        }
    }

    public void Dispose() => _context.Dispose();

    public async Task RollbackTransactionAsync()
    {
        if(_currentTransaction is null)
        {
            throw new InvalidOperationException("A transaction must be in progress to execute rollback.");
        }
        try
        {
            await _currentTransaction.RollbackAsync();
        } 
        finally
        {
            await _currentTransaction.DisposeAsync();
            _currentTransaction = null;
        }
    }

    public Task SaveChanges() => _context.SaveChangesAsync();
}
