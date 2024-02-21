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
    public bool HasActiveTransaction => throw new NotImplementedException();

    public UnitOfWork(AppDbContext context) {
        _context = context;

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

    public Task RollbackTransactionAsync()
    {
        throw new NotImplementedException();
    }

    public Task SaveChanges() => _context.SaveChangesAsync();
}
